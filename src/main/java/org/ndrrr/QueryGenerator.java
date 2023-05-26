package org.ndrrr;

import org.ndrrr.model.Field;
import org.ndrrr.model.Relationship;
import org.ndrrr.model.Table;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class QueryGenerator {

    public String generateQuery(List<Field> requiredFields, List<Field> queryParams) {
        List<Table> requiredTables = resolveRequiredTables(requiredFields);
        Table startingTable = queryParams.get(0).getTable();
        Graph graph = new Graph();
        HashMap<Table, Table> pathMap = graph.bfs(startingTable);
        HashSet<Table> tablesForJoins = getTablesForJoins(pathMap, requiredTables);
        return constructQueryFromPathMap(pathMap, tablesForJoins, startingTable, requiredFields, queryParams);
    }

    private HashSet<Table> getTablesForJoins(HashMap<Table, Table> pathMap, List<Table> requiredTables) {
        HashSet<Table> tablesForJoins = new HashSet<>();
        requiredTables.forEach(t -> tablesForJoins.addAll(getPathToRoot(pathMap, t)));
        return tablesForJoins;
    }

    private HashSet<Table> getPathToRoot(HashMap<Table, Table> pathMap, Table t) {
        HashSet<Table> pathToRoot = new HashSet<>();
        Table temp = t;
        while (!temp.getId().equals(-1L)) {
            pathToRoot.add(temp);
            temp = pathMap.get(temp);
        }
        return pathToRoot;
    }

    private List<Table> resolveRequiredTables(List<Field> requiredFields) {
        return requiredFields.stream()
                .map(Field::getTable)
                .distinct()
                .collect(Collectors.toList());
    }

    private String constructQueryFromPathMap(
            HashMap<Table, Table> pathMap,
            HashSet<Table> tablesForJoins,
            Table startingTable,
            List<Field> requiredFields,
            List<Field> queryParams
    ) {
        System.out.println("Starting table: " + startingTable);
        System.out.println("All tables: ");
        pathMap.forEach((key, value) -> System.out.println(key.toString() + " " + value.getId()));
        HashSet<Table> joinedTables = new HashSet<>();
        joinedTables.add(startingTable);
        return "SELECT " +
                constructSelect(requiredFields) +
                " FROM " +
                startingTable.getName() +
                " " +
                constructJoins(pathMap, joinedTables, tablesForJoins) +
                " WHERE " +
                constructWhere(queryParams);
    }

    private String constructSelect(List<Field> requiredFields) {
        return requiredFields.stream()
                .map(Field::getSelectClause)
                .collect(Collectors.joining(", "));
    }

    private String constructJoins(
            HashMap<Table, Table> pathMap,
            HashSet<Table> joinedTables,
            HashSet<Table> tablesForJoin
    ) {
        return pathMap.entrySet().stream()
                .skip(1) // skipping root
                .filter(entry -> tablesForJoin.contains(entry.getKey()) && tablesForJoin.contains(entry.getValue()))
                .map(entry -> constructSingleJoin(entry.getKey(), entry.getValue(), joinedTables))
                .sorted(Collections.reverseOrder())
                .collect(Collectors.joining(" "));
    }

    private String constructWhere(List<Field> queryParams) {
        return queryParams.stream()
                .map(Field::getWhereClause)
                .collect(Collectors.joining(" AND "));
    }

    private String constructSingleJoin(Table t1, Table t2, HashSet<Table> joinedTables) {
        Relationship r = getRelationship(t1, t2);

        Table newTable = t1;
        if (joinedTables.contains(t1)) {
            newTable = t2;
        }
        StringBuilder joinString = new StringBuilder();
        joinString.append("LEFT JOIN ");
        joinString.append(newTable.getName());
        joinString.append(" ON ");
        for (int i = 0; i < r.getFields1().size(); i++) {
            if (i > 0) joinString.append(" AND ");
            joinString.append(r.getTable1().getName());
            joinString.append(".");
            joinString.append(r.getFields1().get(i).getName());
            joinString.append(" = ");
            joinString.append(r.getTable2().getName());
            joinString.append(".");
            joinString.append(r.getFields2().get(i).getName());
        }

        return joinString.toString();
    }

    private Relationship getRelationship(Table t1, Table t2) {
        return t1.getRelationships().stream()
                .filter(relationship -> isRelated(relationship, t2))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No relationship found between " + t1.getName() + " and " + t2.getName()));
    }

    private Boolean isRelated(Relationship r, Table t) {
        return r.getTable1().equals(t) || r.getTable2().equals(t);
    }

}

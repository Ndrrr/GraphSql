package org.ndrrr;

import org.ndrrr.model.Relationship;
import org.ndrrr.model.Table;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {

    public HashMap<Table, Table> bfs(Table startingTable) {
        HashMap<Table, Boolean> visited = new HashMap<>();
        HashMap<Table, Table> parent = new HashMap<>();

        Queue<Table> queue = new LinkedList<>();
        queue.add(startingTable);
        Table root = new Table();
        root.setId(-1L);
        parent.put(startingTable, root);
        while (!queue.isEmpty()) {
            Table table = queue.poll();
            visited.put(table, true);
            for (Relationship relationship : table.getRelationships()) {
                Table child = relationship.getTable1().equals(table) ?
                        relationship.getTable2() : relationship.getTable1();
                if (!visited.containsKey(child)) {
                    queue.add(child);
                    parent.put(child, table);
                }
            }
        }
        return parent;
    }
}

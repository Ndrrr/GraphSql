package org.ndrrr;

import org.ndrrr.model.Field;
import org.ndrrr.model.Relationship;
import org.ndrrr.model.Table;

import java.util.List;

public class Example {

    public static void main(String[] args) {
        /*
         * tables:
         *  t1(id, name, pan, age)
         *  t2(id, name, accountNumber, age)
         *  t3(id, name, pan, accountNumber)
         *
         * relationships:
         *  t1 - t2 (name, age)
         *  t1 - t3 (pan, name)
         *  t2 - t3 (accountNumber)
         */
        //<editor-fold desc="Create Test Data">
        Table table1 = new Table();
        table1.setId(1L);
        table1.setName("t1");
        Table table2 = new Table();
        table2.setId(2L);
        table2.setName("t2");
        Table table3 = new Table();
        table3.setId(3L);
        table3.setName("t3");

        Field t1Id = new Field();
        t1Id.setId(1L);
        t1Id.setName("id");
        t1Id.setTable(table1);
        Field t1Name = new Field();
        t1Name.setId(2L);
        t1Name.setName("name");
        t1Name.setTable(table1);
        Field t1Pan = new Field();
        t1Pan.setId(3L);
        t1Pan.setName("pan");
        t1Pan.setTable(table1);
        Field t1Age = new Field();
        t1Age.setId(4L);
        t1Age.setName("age");
        t1Age.setTable(table1);

        Field t2Id = new Field();
        t2Id.setId(5L);
        t2Id.setName("id");
        t2Id.setTable(table2);
        Field t2Name = new Field();
        t2Name.setId(6L);
        t2Name.setName("name");
        t2Name.setTable(table2);
        Field t2accountNumber = new Field();
        t2accountNumber.setId(7L);
        t2accountNumber.setName("accountNumber");
        t2accountNumber.setTable(table2);
        Field t2age = new Field();
        t2age.setId(8L);
        t2age.setName("age");
        t2age.setTable(table2);

        Field t3id = new Field();
        t3id.setId(9L);
        t3id.setName("id");
        t3id.setTable(table3);
        Field t3name = new Field();
        t3name.setId(10L);
        t3name.setName("name");
        t3name.setTable(table3);
        Field t3pan = new Field();
        t3pan.setId(11L);
        t3pan.setName("pan");
        t3pan.setTable(table3);
        Field t3accountNumber = new Field();
        t3accountNumber.setId(12L);
        t3accountNumber.setName("accountNumber");
        t3accountNumber.setTable(table3);

        Relationship relationship1 = new Relationship();
        relationship1.setTable1(table1);
        relationship1.setTable2(table2);
        relationship1.setFields1(List.of(t1Age, t1Name));
        relationship1.setFields2(List.of(t2age, t2Name));

        Relationship relationship2 = new Relationship();
        relationship2.setTable1(table2);
        relationship2.setTable2(table3);
        relationship2.setFields1(List.of(t2accountNumber));
        relationship2.setFields2(List.of(t3accountNumber));

        Relationship relationship3 = new Relationship();
        relationship3.setTable1(table1);
        relationship3.setTable2(table3);
        relationship3.setFields1(List.of(t1Pan, t1Name));
        relationship3.setFields2(List.of(t3pan, t3name));

        table1.setFields(List.of(t1Id, t1Name, t1Pan, t1Age));
        table1.setRelationships(List.of(relationship1, relationship3));
        table2.setFields(List.of(t2Id, t2Name, t2accountNumber, t2age));
        table2.setRelationships(List.of(relationship1, relationship2));
        table3.setFields(List.of(t3id, t3name, t3pan, t3accountNumber));
        table3.setRelationships(List.of(relationship2, relationship3));
        // </editor-fold>
        /*
         * example request:
         *  {
         *      requiredFields: ["t1.id", "t1.name", "t2.pan", "t3.pan"],
         *      searchParam: {t1.Pan: "12345678"}
         *  }
         */
        List<Field> requiredFields = List.of(t2Id, t1Name,  t2age, t3id, t3accountNumber);
        List<Field> queryParams = List.of(t1Pan, t1Age);

        QueryGenerator queryGenerator = new QueryGenerator();
        String query = queryGenerator.generateQuery(requiredFields, queryParams);

        System.out.println(query);
    }

}

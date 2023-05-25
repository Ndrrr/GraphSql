package org.example;

import lombok.Data;

@Data
public class Relationship {

    private Table table1;
    private Table table2;
    private Field field1;
    private Field field2;

    public String toString() {
        return "Relationship(table1=" + this.getTable1().getName() +
                ", table2=" + this.getTable2().getName() +
                ", field1=" + this.getField1().getName() +
                ", field2=" + this.getField2().getName() +
                ")";
    }

}

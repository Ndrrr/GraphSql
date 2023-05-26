package org.ndrrr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Relationship {

    private Table table1;
    private Table table2;
    private List<Field> fields1;
    private List<Field> fields2;

    public String toString() {
        return "Relationship(table1=" + this.getTable1().getName() +
                ", table2=" + this.getTable2().getName() +
                ", field1=" + this.getFields1().toString() +
                ", field2=" + this.getFields2().toString() +
                ")";
    }

}

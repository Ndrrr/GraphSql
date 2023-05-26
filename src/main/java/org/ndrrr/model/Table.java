package org.ndrrr.model;

import lombok.Data;

import java.util.List;

@Data
public class Table {

    private Long id;
    private String name;
    private List<Field> fields;
    private List<Relationship> relationships;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        if (!id.equals(table.id)) return false;
        return name.equals(table.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public String toString() {
        return "Table(id=" + this.getId() +
                ", name=" + this.getName() +
                ", fields=" + this.getFields().stream().map(Field::getName).toList() +
                ", relationships=" + this.getRelationships() +
                ")";
    }
}

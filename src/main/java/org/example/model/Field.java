package org.example.model;

import lombok.Data;

@Data
public class Field {

    private Long id;
    private String name;
    private Table table;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (!id.equals(field.id)) return false;
        return name.equals(field.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public String toString() {
        return "Field(id=" + this.getId() +
                ", name=" + this.getName() +
                ", table=" + this.getTable().getName() +
                ")";
    }
}

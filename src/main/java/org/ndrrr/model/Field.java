package org.ndrrr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Field {

    private Long id;
    private String name;
    private Table table;
    private Object defaultValue;

    public Field(Long id, String name, Table table) {
        this.id = id;
        this.name = name;
        this.table = table;
    }

    public String getSelectClause() {
        return this.getTable().getName() + "." + this.getName();
    }

    public String getWhereClause() {
        return this.getTable().getName() + "." + this.getName() + " = " + this.resolveValue();
    }

    private String resolveValue() {
        if (this.getDefaultValue() == null)
            return "?";
        return this.getDefaultValue().toString();
    }

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

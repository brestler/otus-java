package ru.otus.jdbc.query;

import java.util.Map;

public class InsertQuery implements Query {

    private String query;
    private Map<Class<?>, Object> parameters;

    public InsertQuery(String query, Map<Class<?>, Object> parameters) {
        this.query = query;
        this.parameters = parameters;
    }

    public String getQuery() {
        return query;
    }

    public Map<Class<?>, Object> getParameters() {
        return parameters;
    }
}

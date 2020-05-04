package ru.otus.jdbc.query;

public class UpdateQuery implements Query {

    private final String query;
    private final long id;

    public UpdateQuery(String query, long id) {
        this.query = query;
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public long getId() {
        return id;
    }
}

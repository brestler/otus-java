package ru.otus.jdbc.query;

public interface QueryCreator<T> {

    InsertQuery getInsertQuery(T object);

    String getSelectQuery(Class<T> clazz);

    UpdateQuery getUpdateQuery(T newObject);
}

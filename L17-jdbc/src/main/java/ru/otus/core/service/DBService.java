package ru.otus.core.service;

public interface DBService<T> {

    void create(T object);

    void update(T object);

    T load(long id, Class<T> clazz);

}

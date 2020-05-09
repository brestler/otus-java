package ru.otus.core.service;

import java.util.Optional;

public interface DBService<T> {

    Optional<T> load(long id, Class<T> clazz);

    long save(T object);
}

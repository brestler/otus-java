package ru.otus.core.dao;

import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface Dao<T> {

  Optional<T> load(long id, Class<T> clazz);

  long save(T object);

  SessionManager getSessionManager();
}

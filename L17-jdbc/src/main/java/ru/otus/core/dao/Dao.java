package ru.otus.core.dao;

import ru.otus.core.sessionmanager.SessionManager;

public interface Dao<T> {

  T load(long id, Class<T> clazz);

  void create(T objectData);

  void update(T objectData);

  SessionManager getSessionManager();
}

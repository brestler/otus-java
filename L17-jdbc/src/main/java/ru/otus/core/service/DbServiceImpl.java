package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.Dao;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceImpl<T> implements DBService<T> {
    private static Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);

    private final Dao<T> dao;

    public DbServiceImpl(Dao<T> dao) {
        this.dao = dao;
    }

    @Override
    public void create(T object) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.create(object);
                sessionManager.commitSession();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void update(T object) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.update(object);
                sessionManager.commitSession();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public T load(long id, Class<T> clazz) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return dao.load(id, clazz);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }
}

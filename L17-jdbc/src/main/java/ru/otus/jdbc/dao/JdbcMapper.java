package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.Dao;
import ru.otus.core.dao.ObjectMapper;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.query.InsertQuery;
import ru.otus.jdbc.query.QueryCreator;
import ru.otus.jdbc.query.UpdateQuery;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcMapper<T> implements Dao<T> {
    private static Logger logger = LoggerFactory.getLogger(JdbcMapper.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;
    private final QueryCreator<T> queryCreator;
    private final ObjectMapper<T> objectMapper;

    public JdbcMapper(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor, QueryCreator<T> queryCreator, ObjectMapper<T> objectMapper) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.queryCreator = queryCreator;
        this.objectMapper = objectMapper;
    }


    @Override
    public T load(long id, Class<T> clazz) {
        String selectQuery = queryCreator.getSelectQuery(clazz);
        try {
            return dbExecutor.selectRecord(getConnection(), selectQuery, id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return objectMapper.mapToObject(resultSet);
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    @Override
    public void create(T objectData) {
        InsertQuery insertQuery = queryCreator.getInsertQuery(objectData);
        try {
            dbExecutor.insertRecord(getConnection(), insertQuery);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(T objectData) {
        UpdateQuery updateQuery = queryCreator.getUpdateQuery(objectData);
        try {
            dbExecutor.updateRecord(getConnection(), updateQuery);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }

    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}

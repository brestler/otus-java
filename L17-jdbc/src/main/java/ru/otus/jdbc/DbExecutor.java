package ru.otus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.query.InsertQuery;
import ru.otus.jdbc.query.Query;
import ru.otus.jdbc.query.UpdateQuery;

import java.sql.*;
import java.util.Map;
import java.util.function.Function;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);

    public void insertRecord(Connection connection, InsertQuery query) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(query.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            for (Map.Entry<Class<?>, Object> entry : query.getParameters().entrySet()) {
                Class<?> type = entry.getKey();
                if (type.equals(Boolean.class) || type.equals(boolean.class))
                    pst.setBoolean(++i, (Boolean) entry.getValue());
                else if (type.equals(Byte.class) || type.equals(byte.class))
                    pst.setByte(++i, (Byte) entry.getValue());
                else if (type.equals(Short.class) || type.equals(short.class))
                    pst.setShort(++i, (Short) entry.getValue());
                else if (type.equals(Integer.class) || type.equals(int.class))
                    pst.setInt(++i, (Integer) entry.getValue());
                else if (type.equals(Long.class) || type.equals(long.class))
                    pst.setLong(++i, (Long) entry.getValue());
                else if (type.equals(Float.class) || type.equals(float.class))
                    pst.setFloat(++i, (Float) entry.getValue());
                else if (type.equals(Double.class) || type.equals(double.class))
                    pst.setDouble(++i, (Double) entry.getValue());
                else if (type.equals(String.class))
                    pst.setString(++i, (String) entry.getValue());
            }
            pst.executeUpdate();
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    public T selectRecord(Connection connection, String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rsHandler.apply(rs);
            }
        }
    }

    public void updateRecord(Connection connection, UpdateQuery updateQuery) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(updateQuery.getQuery())) {
            pst.setLong(1, updateQuery.getId());
            pst.executeUpdate();
        }
    }
}

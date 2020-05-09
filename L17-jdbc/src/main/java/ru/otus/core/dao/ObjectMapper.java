package ru.otus.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper<T> {

    T mapToObject(ResultSet resultSet) throws SQLException;

}

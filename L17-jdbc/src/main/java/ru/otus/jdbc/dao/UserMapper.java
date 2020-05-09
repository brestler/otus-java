package ru.otus.jdbc.dao;

import ru.otus.core.dao.ObjectMapper;
import ru.otus.core.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User mapToObject(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("age"));
    }
}

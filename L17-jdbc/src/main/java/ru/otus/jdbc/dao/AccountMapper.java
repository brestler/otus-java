package ru.otus.jdbc.dao;

import ru.otus.core.dao.ObjectMapper;
import ru.otus.core.model.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements ObjectMapper<Account> {

    @Override
    public Account mapToObject(ResultSet resultSet) throws SQLException {
        return new Account(resultSet.getLong("no"),
                resultSet.getString("type"),
                resultSet.getInt("rest"));
    }
}

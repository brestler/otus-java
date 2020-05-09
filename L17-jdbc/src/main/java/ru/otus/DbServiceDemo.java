package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.Dao;
import ru.otus.core.dao.ObjectMapper;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.service.DBService;
import ru.otus.core.service.DbServiceImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.dao.AccountMapper;
import ru.otus.jdbc.dao.JdbcMapper;
import ru.otus.jdbc.dao.UserMapper;
import ru.otus.jdbc.query.QueryCreator;
import ru.otus.jdbc.query.QueryCreatorImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        DataSource dataSource = new DataSourceH2();
        DbServiceDemo demo = new DbServiceDemo();

        demo.createTable(dataSource);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<User> dbExecutor = new DbExecutor<>();
        QueryCreator<User> queryCreator = new QueryCreatorImpl<>();
        ObjectMapper<User> userMapper = new UserMapper();
        Dao<User> dao = new JdbcMapper<>(sessionManager, dbExecutor, queryCreator, userMapper);

        DBService<User> dbService = new DbServiceImpl<>(dao);
        dbService.create(new User(0, "dbServiceUser", 4));
        dbService.create(new User(0, "Hey", 20));
        User user1 = dbService.load(1, User.class);
        User user2 = dbService.load(2, User.class);

        logger.info("First user created - {}", user1);
        logger.info("Second user created - {}", user2);

        DbExecutor<Account> dbAccountExecutor = new DbExecutor<>();
        QueryCreator<Account> accountQueryCreator = new QueryCreatorImpl<>();
        ObjectMapper<Account> accountMapper = new AccountMapper();
        Dao<Account> accountDao = new JdbcMapper<>(sessionManager, dbAccountExecutor, accountQueryCreator, accountMapper);

        DBService<Account> accountDbService = new DbServiceImpl<>(accountDao);
        accountDbService.create(new Account(0, "Account1", 6));
        accountDbService.create(new Account(0, "BlaBla", 40));
        Account account1 = accountDbService.load(1, Account.class);
        Account account2 = accountDbService.load(2, Account.class);

        logger.info("First account created - {}", account1);
        logger.info("Second account created - {}", account2);

        accountDbService.update(new Account(2, "newTypeBlaBla", 10));
        Account account2Updated = accountDbService.load(2, Account.class);
        logger.info("Second account updated - {}", account2Updated);
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))");
             PreparedStatement pst2 = connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
            pst2.executeUpdate();
        }
        System.out.println("tables created");
    }
}

package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.Dao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBService;
import ru.otus.core.service.DbServiceImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.DaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Collections;
import java.util.Optional;


public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);


        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        Dao<User> userDao = new DaoHibernate<>(sessionManager);
        DBService<User> dbServiceUser = new DbServiceImpl<>(userDao);


        PhoneDataSet phones = new PhoneDataSet("+7-000-000-00-00");
        User user = new User("Вася", 10,
                new AddressDataSet("Тверская"), Collections.singletonList(phones));
        // Могу я избежать этой циклической ссылки юзера и телефона?
        phones.setUser(user);

        long id = dbServiceUser.save(user);
        Optional<User> mayBeCreatedUser = dbServiceUser.load(id, User.class);
        logger.info("Got user {}", mayBeCreatedUser);
    }

}

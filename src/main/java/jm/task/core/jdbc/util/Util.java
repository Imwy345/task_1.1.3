package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    private static final String URL = "jdbc:postgresql://localhost:5432/task1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "zs462282x";

    private static SessionFactory buildSessionFactory() {
        try {
            Properties settings = new Properties();
            settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            settings.put("hibernate.connection.url", URL);
            settings.put("hibernate.connection.username", USER);
            settings.put("hibernate.connection.password", PASSWORD);
            settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            settings.put("hibernate.hbm2ddl.auto", "update"); // Или "create", "validate"
            settings.put("hibernate.show_sql", "true"); // Показывать SQL-запросы в консоли

            return new Configuration()
                    .addProperties(settings)
                    .addAnnotatedClass(User.class) // Добавьте вашу сущность User
                    .buildSessionFactory();

        } catch (HibernateException ex) {
          logger.log(Level.SEVERE, "Ошибка при подключении к БД с помощью Hibernate");

        }
        return null;
    }

public static SessionFactory getSessionFactory() {
    return sessionFactory;
}


public static Connection getConnection() {
    try {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (SQLException e) {
        throw new RuntimeException("Ошибка подключения к базе данных", e);
    }
}
}


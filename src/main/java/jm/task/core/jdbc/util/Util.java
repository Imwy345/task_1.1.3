package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties settings = new Properties();
            settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            settings.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/task1");
            settings.put("hibernate.connection.username", "postgres");
            settings.put("hibernate.connection.password", "zs462282x");
            settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            settings.put("hibernate.hbm2ddl.auto", "update"); // Или "create", "validate"
            settings.put("hibernate.show_sql", "true"); // Показывать SQL-запросы в консоли

            return new Configuration()
                    .addProperties(settings)
                    .addAnnotatedClass(User.class) // Добавьте вашу сущность User
                    .buildSessionFactory();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}


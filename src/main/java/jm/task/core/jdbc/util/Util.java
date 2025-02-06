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

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
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


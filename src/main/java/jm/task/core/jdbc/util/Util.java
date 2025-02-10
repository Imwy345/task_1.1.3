package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    private static Connection connection;//Добавил поле connection
    private static final String URL = "jdbc:postgresql://localhost:5432/task1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "zs462282x";


    /*добавил реализацию подключения*/
    private static void buildConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.severe("Ошибка подключения к базе данных");
        }
    }

    /*Добавил проверку*/
    public static Connection getConnection() {
        if (connection == null) {
            buildConnection();
        }
        return connection;
    }
}

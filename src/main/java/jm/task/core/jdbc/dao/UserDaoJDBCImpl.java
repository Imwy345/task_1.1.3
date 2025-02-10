package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    @Override
    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "lastName VARCHAR(50), " +
                    "age SMALLINT)");
            logger.info("Создана таблица users");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при создании таблицы users: ", e);

        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            logger.info("Таблица users удалена");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при удалении таблицы users: ", e);

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement(
                "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            logger.info("Пользователь " + name + " " + lastName + "  сохранен");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при сохранении пользователя " + name + " " + lastName + ": ", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            if (statement.executeUpdate() > 0) {
                logger.info("Пользователь с id " + id + " успешно удален");
            } else {
                logger.warning("Пользователь с id " + id + " не найден.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при удалении пользователя с id " + id + ": ", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            logger.info("Получен список пользователей");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении списка пользователей: ", e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM users");
            logger.info("Таблица users очищена.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при очистке таблицы users: ", e);
        }
    }
}


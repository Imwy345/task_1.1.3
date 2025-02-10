package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "lastName VARCHAR(50), " +
                    "age SMALLINT)").executeUpdate();
            transaction.commit();
            logger.info("Создана таблица users");

        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.log(Level.SEVERE, "Ошибка при создании таблицы users: ", e);
        }
    }


    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            logger.info("Таблица users удалена");

        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.log(Level.SEVERE, "Ошибка при удалении таблицы users: ", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            logger.info("Пользователь " + name + " " + lastName + "  сохранен");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                logger.log(Level.SEVERE, "Ошибка при сохранении пользователя " + name + " " + lastName + ": ", e);
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                logger.info("Пользователь с id " + id + " успешно удален");
            } else {
                logger.warning("Пользователь с id " + id + " не найден");
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.log(Level.SEVERE, "Ошибка при удалении пользователя с id " + id + ": ", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null; // Инициализируем список пользователей
        try (Session session = Util.getSessionFactory().openSession()) {
            users = session.createQuery("FROM User", User.class).list();
            logger.info("Получен список пользователей");
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка при получении списка пользователей: ", e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            logger.info("Таблица users очищена.");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.log(Level.SEVERE, "Ошибка при очистке таблицы users: ", e);
        }
    }
}

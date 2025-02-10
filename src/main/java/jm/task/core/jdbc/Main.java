package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Alice", "Smith", (byte) 25);
        userService.saveUser("Bob", "Johnson", (byte) 30);
        userService.saveUser("Charlie", "Brown", (byte) 35);
        userService.saveUser("David", "Wilson", (byte) 40);
        System.out.println("Users list:");
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}


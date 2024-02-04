package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    List<User> listUsers();

    User findUserById(Long id);

    void deleteUser(Long id);

    void updateUser(Long id, User user);
}

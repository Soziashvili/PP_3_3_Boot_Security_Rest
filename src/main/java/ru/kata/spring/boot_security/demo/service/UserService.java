package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    void save(User user);

    List<User> listUsers();

    User findUserById(Long id);

    void deleteUser(Long id);

    void updateUser(Long id, User user);

    User findByUsername(String username);
}

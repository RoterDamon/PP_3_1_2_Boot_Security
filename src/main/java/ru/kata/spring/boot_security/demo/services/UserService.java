package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    User findUserById(Long id);

    boolean saveUser(User user);

    void updateUser(User user);

    boolean deleteUser(Long id);
}

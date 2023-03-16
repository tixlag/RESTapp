package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    User get(int id);
    List<User> getAll();
    void edit(Long id, User user);
    void edit(Long id, String name, String lastName, byte age);

    void delete(Long id);

    User getByName(String name);
}

package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    void add(User user);
    User get(int id);
    List<User> getAll();
    void edit(Long id, User user);
    void edit(Long id, String name, String lastName, byte age,
              String username, String password, Set<Role> roles);

    void delete(Long id);

    User getByName(String name);

    void edit(User user);
}

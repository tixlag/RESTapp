package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserDao {
    void add(User user);
    User get(Long id);
    List<User> getAll();

    void edit(Long id, String name, String lastName, byte age,
              String username, String password, Set<Role> roles);

    void edit(User user);

    void delete(Long id);

    Optional<User> getByUsername(String name);

//    boolean isFind(String name);


}

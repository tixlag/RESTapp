package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);
    User get(int id);
    List<User> getAll();
    void edit(Long id, User user);

    void edit(Long id, String name, String lastName, byte age);

    void delete(Long id);
}

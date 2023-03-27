package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    void add(User user);
    void addWithHiddenRoles(User user);
    User get(int id);
    List<User> getAll();
    void edit(User user);

    void edit(Long id, String name, String lastName, byte age,
              String username, String password, List<Long> roles);

    void delete(Long id);


}

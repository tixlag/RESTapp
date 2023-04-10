package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.UserExistException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@Transactional
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserDao userDao, RoleDao roleDao,@Lazy PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void editUser(User user) {
        Optional<User> curUser = userDao.getByUsername(user.getUsername());
        if (curUser.isEmpty() || curUser.get().getId().equals(user.getId())) {
            user.setPassword(user.getPassword() == null
                    ? userDao.get(user.getId()).getPassword()
                    : passwordEncoder.encode(user.getPassword()));
            extractEntityRoles(user);
            userDao.edit(user);
        } else {
            throw new UserExistException("Username is busy.");
        }

    }

    @Override
    public User addUser(User user) {
        // Если такого юзернэйма нет, то добавляем
        if (userDao.getByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            extractEntityRoles(user);
            User newUser = userDao.add(user);
            return newUser;
        } else {
            throw new UserExistException("Username is busy");
        }

    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    private void extractEntityRoles(User user) {
        user.setRoles(user.getRoles().stream()
                .map(x -> roleDao.getByName(x.getAuthority()))
                .collect(Collectors.toSet()));
    }
}

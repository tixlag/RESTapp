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
    public void add(User user) {
        // Если такого юзернэйма нет, то добавляем
        if (userDao.getByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.add(user);
        } else {
            throw new UserExistException("Username is busy");
        }

    }

    @Override
    public User get(Long id) {
        return userDao.get(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void edit(User user) {
        userDao.edit(user);
    }

    @Override
    public void edit(Long id, String name, String lastName, byte age,
                     String username, String password, List<Long> roles) {
        Optional<User> user = userDao.getByUsername(username);
        if (user.isEmpty() || user.get().getId().equals(id)) {
            editValidUser(id, name, lastName, age, username, password, roles);
        } else {
            throw new UserExistException("Username is busy.");

        }
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.getByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не существует.");
        }
        // Обман ленивой загрузки
        System.out.println(user.get().getRoles().size());
        return user.get();
    }

    @Override
    public void addWithHiddenRoles(User user) {
            Set<Role> rolesEntity = new HashSet<>();
            for (Role role : user.getRoles()) {
                rolesEntity.add(roleDao.get(Long.parseLong(role.getAuthority())));
            }
            user.setRoles(rolesEntity);
            add(user);
    }

    public void firstRun() {
        add(new User(null, "name", "lastName", (byte) 10, "nadmin", "nadmin", roleDao.getAll()));
    }

    public void editValidUser(Long id, String name, String lastName, byte age,
                              String username, String password, List<Long> roles) {
        if (password != null) {
            password = passwordEncoder.encode(password);
        }

        userDao.edit(id, name, lastName, age, username, password, roleDao.getRoleSet(roles));
    }
}

package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Component
@Transactional
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImp(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public User get(int id) {
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
        userDao.edit(id, name, lastName, age, username, password, roleDao.getRoleSet(roles));
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userDao.getByName(username));

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не существует.");
        }

        return user.get();
    }

    public void addWithHiddenRoles(User user) {
        Set<Role> rolesEntity = new HashSet<>();
        for (Role role : user.getRoles()) {
            rolesEntity.add(roleDao.get(Long.parseLong(role.getAuthority())));
        }
        user.setRoles(rolesEntity);
        add(user);
    }
}

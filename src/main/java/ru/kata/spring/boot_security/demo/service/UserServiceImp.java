package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;


@Component
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    UserDao userDao;
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
    public void edit(Long id, User user) {
        userDao.edit(id, user);
    }

    @Override
    public void edit(Long id, String name, String lastName, byte age) {
        userDao.edit(id, name, lastName, age);
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
}

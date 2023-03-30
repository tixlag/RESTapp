package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    EntityManager manager;


    @Override
    public void add(User user) {
        manager.persist(user);
    }

    @Override
    public User get(Long id) {
//        return manager.getObject() .createQuery("from User", User.class).getSingleResult();
        return manager.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return manager.createQuery("from User", User.class).getResultList();
    }



    @Override
    public void edit(Long id, String name, String lastName, byte age,
                     String username, String password, Set<Role> roles) {
        User currentUser = manager.find(User.class, id);
        currentUser.setName(name);
        currentUser.setLastName(lastName);
        currentUser.setAge(age);
        currentUser.setUsername(username);
        if (password != null) {
            currentUser.setPassword(password);
        }
        currentUser.setRoles(roles);
    }

    @Override
    public void edit(User user) {
        manager.merge(user);
    }

    @Override
    public void delete(Long id) {
        manager.remove(manager.find(User.class, id));
    }

    @Override
    public Optional<User> getByUsername(String name) {
        TypedQuery<User> query = manager.createQuery("from User where username=:name", User.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ignore) {
            return Optional.empty();
        }
    }

//    @Override
//    public boolean isFind(String name) {
//        // ну а как еще? В javax.persistence
//        // нет ни для Query ни для Manager подходящих методов
//        // Я же не по ключю смотрю.
//        Query query = manager.createQuery("from User where username=:name");
//        query.setParameter("name", name);
//        return !query.getResultList().isEmpty();
//    }
}

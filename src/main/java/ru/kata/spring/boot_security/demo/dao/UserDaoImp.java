package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    EntityManager manager;


    @Override
    public void add(User user) {
        manager.persist(user);
    }

    @Override
    public User get(int id) {
//        return manager.getObject() .createQuery("from User", User.class).getSingleResult();
        manager.createQuery("from User");
        return null;
    }

    @Override
    public List<User> getAll() {
        return (List<User>) manager.createQuery("from User").getResultList();
    }

    @Override
    public void edit(Long id, User user) {

    }

    @Override
    public void edit(Long id, String name, String lastName, byte age,
                     String username, String password, Set<>) {
        User currentUser = manager.find(User.class, id);
        currentUser.setName(name);
        currentUser.setLastName(lastName);
        currentUser.setAge(age);
        currentUser.setUsername();
    }

    @Override
    public void delete(Long id) {
        manager.remove(manager.find(User.class, id));
    }

    @Override
    public User getByName(String name) {
        TypedQuery query = manager.createQuery("from User where username=:name", User.class);
        query.setParameter("name", name);
        return (User) query.getSingleResult();
    }
}

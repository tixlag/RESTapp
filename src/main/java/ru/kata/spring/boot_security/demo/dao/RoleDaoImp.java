package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDaoImp implements RoleDao{

    @PersistenceContext
    EntityManager manager;

    @Override
    public Set<Role> getAll() {
        return new HashSet<Role>(manager.createQuery("from Role").getResultList());
    }

    @Override
    public Role get(Long id) {
        return manager.find(Role.class, id);
    }
}

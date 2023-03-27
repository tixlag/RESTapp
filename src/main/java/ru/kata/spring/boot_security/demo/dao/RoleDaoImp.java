package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public Set<Role> getRoleSet(List<Long> roles) {
        TypedQuery<Role> query = manager.createQuery("from Role as role where role.id in ?1", Role.class);
        query.setParameter(1, roles);
        return new HashSet<>(query.getResultList());
//         return manager.createQuery("from Role", Role.class).getResultList()
//                .stream()
//                .filter(x -> roles.contains(x.getId().toString()))
//                .collect(Collectors.toSet());
    }
}

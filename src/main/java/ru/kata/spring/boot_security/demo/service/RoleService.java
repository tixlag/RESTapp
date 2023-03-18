package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> getAll();
}

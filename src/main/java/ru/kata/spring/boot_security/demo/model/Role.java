package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    private Long id;
@Column(name = "name")
    private String name;

    public Role(){};
    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setAuthority(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return name.substring(5).toLowerCase();
    }


    public Long getId() { return id; }
}

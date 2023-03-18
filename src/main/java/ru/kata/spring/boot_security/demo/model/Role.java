package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    private Long id;
@Column(name = "name")
    private String authority;
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;
    public Role(){};

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String name) {
        this.authority = name;
    }


    public Long getId() { return id; }
}

package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    private Long id;
    @Column(name = "name")
    private String authority;

    public Role(){};
    public Role(String authority) {
        this.authority = authority;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String name) {
        this.authority = name;
    }

    public String getSimpleName() {
        return authority.substring(5).toLowerCase();
    }

}

package ru.kata.spring.boot_security.demo.dto;

import javax.validation.constraints.NotEmpty;

public class RoleDTO {
    @NotEmpty
    private String authority;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return authority;
    }
}

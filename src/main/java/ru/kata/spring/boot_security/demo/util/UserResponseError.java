package ru.kata.spring.boot_security.demo.util;

public class UserResponseError {
    private String message;

    public UserResponseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

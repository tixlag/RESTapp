package ru.kata.spring.boot_security.demo.util;

public class UserExistException  extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }

}

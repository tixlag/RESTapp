package ru.kata.spring.boot_security.demo.configs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.kata.spring.boot_security.demo.util.UserExistException;
import ru.kata.spring.boot_security.demo.util.UserResponseError;



@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<UserResponseError> handleException(UserExistException e) {
        UserResponseError response = new UserResponseError(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

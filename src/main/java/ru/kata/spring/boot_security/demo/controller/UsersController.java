package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UsersController {

    final
    UserService userService;
    final
    RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String viewUser() {
        return "rest_user";
    }

    @GetMapping("/admin")
    public String adminPanel() {
        return "/admin/rest_admin";
    }

//    @GetMapping("/")
//    public String index() {
//        userService.firstRun();
//
//        return "index";
//    }
}
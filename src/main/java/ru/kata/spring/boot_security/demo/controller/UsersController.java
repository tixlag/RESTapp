package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
//@RequestMapping("/users")
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

    @GetMapping("/")
    public String index() {
        userService.firstRun();

        return "index";
    }

    @GetMapping("/user")
    public String viewUser(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            model.addAttribute("this_user", auth.getPrincipal());
            return "new_user";
    }

    @GetMapping("/admin")
    public String adminPanel(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("users", userService.getAll());
        model.addAttribute("this_user", (User) auth.getPrincipal());
        model.addAttribute("newUser", new User());
        model.addAttribute("defRoles", roleService.getAll());
        return "/admin/new_admin";

    }

    @PostMapping("/admin/new")
    public void addUser(HttpServletResponse httpServletResponse,
                        @ModelAttribute  User newUser,
                        ModelMap model) throws IOException {
//        userService.addWithRoles(user, roles);
        userService.addWithHiddenRoles(newUser);
        httpServletResponse.setStatus(200);
        httpServletResponse.sendRedirect("/admin");
    }

    @PatchMapping("/admin/edit/{id}")
    public void newEditUser(HttpServletResponse httpServletResponse,
                         @PathVariable("id") Long id, @RequestParam("name") String name,
                         @RequestParam("lastName") String lastName,
                         @RequestParam("age") Byte age, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam List<Long> roles) {
        userService.edit(id, name, lastName, age, username, password, roles);
        httpServletResponse.setStatus(200);
    }

    @DeleteMapping("/admin/delete/{id}")
    public void newDeleteUser(HttpServletResponse httpServletResponse, @PathVariable("id") Long id) throws IOException {
        userService.delete(id);
        httpServletResponse.setStatus(200);
    }

    @PatchMapping("/admin/edit/")
    public void editUser(HttpServletResponse httpServletResponse,
                             @RequestParam("user_id") Long id, @RequestParam("name") String name,
                           @RequestParam("last_name") String lastName,
                           @RequestParam("age") Byte age, @RequestParam String username, @RequestParam String password, @RequestParam List<Long> roles) {
        userService.edit(id, name, lastName, age, username, password, roles);
        httpServletResponse.setStatus(200);
    }

    @DeleteMapping("/admin/edit")
    public void deleteUser(HttpServletResponse httpServletResponse, @RequestParam("user_id") Long id) throws IOException {
        userService.delete(id);
        httpServletResponse.setStatus(200);
    }



}

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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        return "index";
    }

    @GetMapping("/user")
    public String viewUser(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.isAuthenticated()) {
            model.addAttribute("user", auth.getPrincipal());
            return "user";
//        } else {
//            model.addAttribute("msg", "А логиниться кто будет???");
//            return "403";
//        }
    }

    @GetMapping("/admin")
    public String adminPanel(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("users", userService.getAll());

        model.addAttribute("newUser", new User());
        model.addAttribute("defRoles", roleService.getAll());
        return "/admin/admin";

    }

    // ??? @RequestBody не работает у меня никак

    @PostMapping("/admin/new")
    public void addUser(HttpServletResponse httpServletResponse,
                        @ModelAttribute  User newUser,
                        ModelMap model) throws IOException {
//        userService.addWithRoles(user, roles);
        userService.addWithHiddenRoles(newUser);
        httpServletResponse.setStatus(200);
        httpServletResponse.sendRedirect("/admin");
    }

    @PatchMapping("/admin/edit/")
    public void editUser(HttpServletResponse httpServletResponse,
                         @RequestParam("user_id") Long id, @RequestParam("name") String name,
                           @RequestParam("last_name") String lastName,
                           @RequestParam("age") Byte age, @RequestParam String username, @RequestParam String password, @RequestParam String[] roles) {
        userService.edit(id, name, lastName, age, username, password, roles);
        httpServletResponse.setStatus(200);
    }

    @DeleteMapping("/admin/edit")
    public void deleteUser(HttpServletResponse httpServletResponse, @RequestParam("user_id") Long id) throws IOException {
        userService.delete(id);
        httpServletResponse.setStatus(200);
//        httpServletResponse.sendRedirect("/admin");
//        return "redirect:/admin";
    }

    // @ModelAttribute получает пустого юзера.
    // В js 79 строка. По разному пробовал - не выходит
    @PatchMapping("/admin/edit2/")
    public String editUserr(@ModelAttribute User user, @RequestParam String[] roles) {
        userService.edit(user);
        return "redirect:/admin";
    }

}

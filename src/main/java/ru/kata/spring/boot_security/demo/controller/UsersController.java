package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
//@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String viewUser(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            model.addAttribute("user", auth.getPrincipal());
            return "user";
        } else {
            model.addAttribute("msg", "А логиниться кто будет???");
            return "403";
        }
    }

    @GetMapping("/admin")
    public String users(ModelMap model) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("ADMIN")) {
            model.addAttribute("users", userService.getAll());
            return "admin";
        } else {
            model.addAttribute("msg", "Низя сюды юзерам.");
            return "403";
        }
    }

    // ??? @RequestBody не работает у меня никак

    @PostMapping("/admin/new")
    public String addUser(@RequestParam("name") String name,
                          @RequestParam("lastName") String lastName,
                          @RequestParam("age") byte age, @ModelAttribute User user, ModelMap model) {
        // ??? Это плохо, что я здесь из модели юзера создаю?
        userService.add(new User(name, lastName, age));
        return "redirect:/admin";
    }

    @PatchMapping("/edit/")
    public String editUser(@RequestParam("user_id") Long id, @RequestParam("name") String name,
                           @RequestParam("last_name") String lastName,
                           @RequestParam("age") Byte age) {
        userService.edit(id, name, lastName, age);
        return "redirect:/admin";
    }

    @DeleteMapping("/edit/")
    public String deleteUser(@RequestParam("user_id") Long id) {
        userService.delete(id);
        return "admin";
    }

}

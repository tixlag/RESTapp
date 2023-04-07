package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.ConverterDTO;
import ru.kata.spring.boot_security.demo.util.UserExistException;
import ru.kata.spring.boot_security.demo.util.UserResponseError;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class AdminRestController {

    private final
    UserService userService;
    private final ConverterDTO converterDTO;

    @Autowired
    public AdminRestController(UserService userService, ConverterDTO converterDTO) {
        this.userService = userService;
        this.converterDTO = converterDTO;
    }

    // Этот метод типа вообще не нужен? Страницу /user/ Таймлифом заполнять? #authentication.getP
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword("");
        return ResponseEntity.ok(converterDTO.converToUserDTO(user));
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usersDTO = userService.getAll().stream().map(converterDTO::converToUserDTO).collect(Collectors.toList());
        return ResponseEntity.ok(usersDTO);
    }

    @PostMapping("/admin/users")
    public void addUser(@RequestBody UserDTO userDTO) {
        userService.addRest(converterDTO.converToUser(userDTO));
    }

    @PatchMapping("/admin/users")
    public void editUser(@RequestBody UserDTO userDTO) {
        userService.editRest(converterDTO.converToUser(userDTO));
    }

    @DeleteMapping("/admin/users/{id}")
    public void deleteUser(HttpServletResponse httpServletResponse, @PathVariable("id") Long id) {
        userService.delete(id);
        httpServletResponse.setStatus(200);
    }
}

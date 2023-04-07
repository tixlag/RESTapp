package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.ConverterDTO;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/users")
public class AdminRestController {

    private final
    UserService userService;
    private final ConverterDTO converterDTO;

    @Autowired
    public AdminRestController(UserService userService, ConverterDTO converterDTO) {
        this.userService = userService;
        this.converterDTO = converterDTO;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usersDTO = userService.getAll().stream().map(converterDTO::converToUserDTO).collect(Collectors.toList());
        return ResponseEntity.ok(usersDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        UserDTO newUser = converterDTO.converToUserDTO(userService.addUser(converterDTO.converToUser(userDTO)));
        return ResponseEntity.ok(newUser);
    }

    @PatchMapping
    public void editUser(@RequestBody UserDTO userDTO) {
        userService.editUser(converterDTO.converToUser(userDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(HttpServletResponse httpServletResponse, @PathVariable("id") Long id) {
        userService.deleteUser(id);
        httpServletResponse.setStatus(200);
    }
}

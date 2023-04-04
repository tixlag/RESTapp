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
import ru.kata.spring.boot_security.demo.util.UserExistException;
import ru.kata.spring.boot_security.demo.util.UserResponseError;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/")
public class AdminRestController {

    private final
    UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminRestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/user")
    public UserDTO viewUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(null);
        return modelMapper.map(user, UserDTO.class);
    }

    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> adminPanel() {
        Map<String, Object> response = new HashMap<>();

        UserDTO thisUser = converToUserDTO(
                (User) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal());
        response.put("this_user", thisUser);

        List<UserDTO> usersDTO = userService.getAll().stream().map(this::converToUserDTO).collect(Collectors.toList());
        response.put("users", usersDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admin/new_user")
    public void addUser(@RequestBody UserDTO userDTO) {
        userService.addRest(converToUser(userDTO));
    }

    @PatchMapping("/admin/edit")
    public void editUser(@RequestBody UserDTO userDTO) {
        userService.editRest((converToUser(userDTO)));
    }

    @DeleteMapping("/admin/delete/{id}")
    public void newDeleteUser(HttpServletResponse httpServletResponse, @PathVariable("id") Long id) {
        userService.delete(id);
        httpServletResponse.setStatus(200);
    }

    @ExceptionHandler
    public ResponseEntity<UserResponseError> handleExceprion(UserExistException e) {
        UserResponseError response = new UserResponseError(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private User converToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO converToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}

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

    @GetMapping("/user")
    public ResponseEntity<UserDTO> viewUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword("");
        return new ResponseEntity<>(converterDTO.converToUserDTO(user), HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> adminPanel() {
        Map<String, Object> response = new HashMap<>();

        UserDTO thisUser = converterDTO.converToUserDTO(
                (User) SecurityContextHolder.getContext().
                        getAuthentication().getPrincipal());
        response.put("this_user", thisUser);

        List<UserDTO> usersDTO = userService.getAll().stream().map(converterDTO::converToUserDTO).collect(Collectors.toList());
        response.put("users", usersDTO);
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

        return responseEntity;
    }

    @PostMapping("/admin")
    public void addUser(@RequestBody UserDTO userDTO) {
        userService.addRest(converterDTO.converToUser(userDTO));
    }

    @PatchMapping("/admin")
    public void editUser(@RequestBody UserDTO userDTO) {
        userService.editRest(converterDTO.converToUser(userDTO));
    }

    @DeleteMapping("/admin/{id}")
    public void newDeleteUser(HttpServletResponse httpServletResponse, @PathVariable("id") Long id) {
        userService.delete(id);
        httpServletResponse.setStatus(200);
    }
}

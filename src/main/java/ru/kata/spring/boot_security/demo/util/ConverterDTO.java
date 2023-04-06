package ru.kata.spring.boot_security.demo.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

@Component
public class ConverterDTO {

    private final ModelMapper modelMapper;

    @Autowired
    public ConverterDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User converToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO converToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}

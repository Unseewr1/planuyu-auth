package com.unseewr1.planuyu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unseewr1.planuyu.dom.User;
import com.unseewr1.planuyu.dto.UserDTO;
import com.unseewr1.planuyu.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;


    public User createUser(UserDTO userDTO) {
        User user = toUser(userDTO);
        userRepository.save(toUser(userDTO));
        return user;
    }

    public User toUser(UserDTO userDTO) {
        return objectMapper.convertValue(userDTO, User.class);
    }

    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

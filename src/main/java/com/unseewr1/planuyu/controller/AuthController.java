package com.unseewr1.planuyu.controller;

import com.unseewr1.planuyu.dom.User;
import com.unseewr1.planuyu.dto.AuthResponseDTO;
import com.unseewr1.planuyu.dto.UserDTO;
import com.unseewr1.planuyu.service.TokenFactory;
import com.unseewr1.planuyu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final TokenFactory tokenFactory;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO);
            return ResponseEntity.ok().body(new AuthResponseDTO(true, "User was registered successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
        try {
            Optional<User> userOptional = userService.findByUsername(userDTO.getUsername());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthResponseDTO(false, "User Not found."));
            }
            User user = userOptional.get();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(userDTO.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDTO(false, "Invalid Password!"));
            }

            String token = tokenFactory.from(user.getId());

            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body(userService.toUserDTO(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signout() {
        try {
            // Assuming session management is handled by Spring Security or similar
            return ResponseEntity.ok().body(new AuthResponseDTO(true, "You've been signed out!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponseDTO(false, e.getMessage()));
        }
    }
}

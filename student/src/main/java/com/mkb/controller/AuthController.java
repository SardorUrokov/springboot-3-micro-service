package com.mkb.controller;

import com.mkb.dto.AuthResponseDTO;
import com.mkb.entity.User;
import com.mkb.dto.AuthRequest;
import com.mkb.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final AuthenticationManager authenticationManager;

    public record AuthResponse(String username, String token) {
    }

    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {

        final var result = service.saveUser(user);
        if (result.endsWith("!")) {
            return ResponseEntity.status(409).body(result);
        }

        final var authResponse = AuthResponseDTO.builder()
                .username(user.getEmail())
                .token(result)
                .role("USER")
                .build();

        return ResponseEntity.status(201).body(authResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> getToken(@RequestBody AuthRequest authRequest) {

        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
                );

        if (authenticate.isAuthenticated()) {

            final var generatedToken = service.generateToken(authRequest.getUsername());
            final var authResponse = AuthResponseDTO.builder()
                    .username(authRequest.username)
                    .token(generatedToken)
                    .role("USER")
                    .build();
            return ResponseEntity.status(200).body(authResponse);
        } else
            throw new RuntimeException("invalid access");
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {

        final var username = service.validateToken(token);
        AuthResponse authResponse = new AuthResponse(username, "Token is valid!");
        return ResponseEntity.status(200).body(authResponse);
    }
}
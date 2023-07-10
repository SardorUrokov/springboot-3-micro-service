package com.alibou.controller;

import com.alibou.entity.User;
import com.alibou.dto.AuthRequest;
import com.alibou.service.AuthService;
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

    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        final var result = service.saveUser(user);

        return result.endsWith("!") ?
                ResponseEntity.status(409).body(result)
                :
                ResponseEntity.status(201).body(result);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> getToken(@RequestBody AuthRequest authRequest) {

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authenticate.isAuthenticated())
            return ResponseEntity.status(200).body(service.generateToken(authRequest.getUsername()));
        else
            throw new RuntimeException("invalid access");
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
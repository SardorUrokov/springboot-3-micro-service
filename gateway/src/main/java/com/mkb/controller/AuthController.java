package com.mkb.controller;

import com.mkb.entity.User;
import com.mkb.service.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RestService restService;

    record AuthRequest(String username, String password){}

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody User user){
        final var response = restService
                .register(user);

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest){

        final var response = restService
                .getToken(
                        authRequest.username,
                        authRequest.password
                );

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }
}
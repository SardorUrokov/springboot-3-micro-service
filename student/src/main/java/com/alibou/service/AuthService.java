package com.alibou.service;

import com.alibou.entity.User;
import com.alibou.entity.Token;
import com.alibou.repository.UserRepository;
import com.alibou.repository.TokenRepository;
import com.alibou.exception.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthService {

    final JwtService jwtService;
    final UserRepository userRepository;
    final TokenRepository tokenRepository;
    final PasswordEncoder passwordEncoder;

    public String saveUser(User user) {

        final var exists = userRepository.existsByEmail(user.getEmail());

        if (!exists) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            final var saved = userRepository.save(user);
            return generateToken(saved.getEmail());

        } else
            return "The user is already registered!";
    }

    public String generateToken(String username) {

        final var generateToken = jwtService.generateToken(username);
        saveToken(username, generateToken);

        return generateToken;
    }

    public String validateToken(String token) {
        jwtService.validateToken(token);
        return jwtService.extractUsername(token);
    }

    public void saveToken(String username, String generatedToken) {

        final var user = userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User", "email", username)
                );

        Token token = Token.builder()
                .generatedToken(generatedToken)
                .user(user)
                .createdAt(new Date())
                .revoked(false)
                .expired(false)
                .build();

        tokenRepository.save(token);
    }
}
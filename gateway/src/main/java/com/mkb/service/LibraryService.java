package com.mkb.service;

import com.mkb.entity.User;
import com.mkb.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final RestService restService;

    public record AuthRequestDTO(String username, String password) {
    }

    public ApiResponse getUsersData(String username, String password) {

        final var response = restService.getToken(
                username,
                password
        );

        final var token = Objects.requireNonNull(response.getBody()).getToken();
        return restService.getUsersData(token);
    }

    public ApiResponse getUsersDataWithRegister(String fullName, String email, String password) {

        User user = User.builder()
                .fullName(fullName)
                .email(email)
                .password(password)
                .build();
        final var response = restService.register(user);

        final var token = Objects.requireNonNull(response.getBody()).getToken();
        return restService.getUsersData(token);
    }

    public ApiResponse getSchools(String username, String password) {
        final var response = restService
                .getToken(
                        username,
                        password
                );

        final var token = Objects.requireNonNull(response.getBody()).getToken();
        return restService.getData(token);
    }

    public ApiResponse getSchoolsWithRegister(String fullName, String email, String password) {

        User user = User.builder()
                .fullName(fullName)
                .email(email)
                .password(password)
                .build();
        final var response = restService.register(user);

        final var token = Objects.requireNonNull(response.getBody()).getToken();
        return restService.getData(token);
    }
}
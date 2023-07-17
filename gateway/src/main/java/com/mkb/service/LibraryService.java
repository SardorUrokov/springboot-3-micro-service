package com.mkb.service;

import com.mkb.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final RestService restService;
    public record AuthRequestDTO(String username, String password) {}

    public ApiResponse getUsersData(String username, String password) {

        final var response = restService.getToken(
                username,
                password
        );

        final var token = Objects.requireNonNull(response.getBody()).getToken();
        return restService.getUsersData(token);
    }

    public ApiResponse getSchools(AuthRequestDTO authRequestDTO) {
        final var response = restService
                .getToken(
                        authRequestDTO.username,
                        authRequestDTO.password
                );
        final var token = Objects.requireNonNull(response.getBody()).getToken();
        return restService.getData(token);
    }
}
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

    public ApiResponse getData(AuthRequestDTO authRequestDTO) {
        final var response = restService
                .getToken(
                        authRequestDTO.username,
                        authRequestDTO.password
                );

        final var data = restService.getData(
                Objects.requireNonNull(
                                response.getBody()
                        )
                        .getToken()
        );
        return data;
    }
}
package com.mkb.service;

import com.mkb.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final RestService restService;
    public record AuthRequestDTO(String username, String password){}

    public ApiResponse getData(AuthRequestDTO authRequestDTO){
//        final var token = restService
//                .getToken(
//                        authRequestDTO.username,
//                        authRequestDTO.password
//                );
//        return restService.getData(token);
        return null;
    }
}
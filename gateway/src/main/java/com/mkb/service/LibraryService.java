package com.mkb.service;

import com.mkb.entity.User;
import com.mkb.response.ApiResponse;
import com.mkb.entity.enums.Permission;
import com.mkb.response.ResponseObject;
import com.mkb.repository.UserRepository;
import com.mkb.controller.LibraryController;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import jakarta.ws.rs.NotFoundException;

import static com.mkb.entity.enums.Permission.*;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final RestService restService;
    private final UserRepository userRepository;

    public record AuthRequestDTO(String username, String password) {
    }

    public ApiResponse saveSchoolWithRegister(LibraryController.SchoolDTO schoolDTO, User user) {

        final var userEmail = Objects.requireNonNull(user.getEmail());
        final var exists = existUser(userEmail);

        if (!exists) {
            final var response = restService.register(user);

            if (isAccessibleUser(userEmail, CREATE)) {
                final var token = Objects.requireNonNull(response.getBody()).getToken();
                return restService.saveSchool(token, schoolDTO);

            } else
                return new ApiResponse(
                        HttpStatus.FORBIDDEN,
                        new ResponseObject("Forbidden", "Method not allowed"));
        } else
            return new ApiResponse(
                    HttpStatus.CONFLICT,
                    new ResponseObject("This user has already Registered!", null));

    }

    public ApiResponse saveSchoolWithAuthenticate(LibraryController.SchoolDTO schoolDTO, AuthRequestDTO authRequestDTO) {

        final var response = restService.getToken(
                authRequestDTO.username,
                authRequestDTO.password
        );
        final var userEmail = authRequestDTO.username;

        if (isAccessibleUser(userEmail, CREATE)) {
            final var token = Objects.requireNonNull(response.getBody()).getToken();
            return restService.saveSchool(token, schoolDTO);

        } else
            return new ApiResponse(
                    HttpStatus.FORBIDDEN,
                    new ResponseObject("Forbidden", "Method not allowed"));
    }

    public ApiResponse getUsersData(String username, String password) {

        final var response = restService.getToken(
                username,
                password
        );

        if (Objects.requireNonNull(response.getBody()).getRole().equals("ADMIN")) {
            final var token = Objects.requireNonNull(response.getBody()).getToken();
            return restService.getUsersData(token);

        } else
            return new ApiResponse(
                    HttpStatus.FORBIDDEN,
                    new ResponseObject("Forbidden", "Method not allowed"));
    }

    public ApiResponse getUsersDataWithRegister(String fullName, String email, String password) {

        final var exists = existUser(email);
        if (!exists) {

            User user = User.builder()
                    .fullName(fullName)
                    .email(email)
                    .password(password)
                    .build();

            final var response = restService.register(user);

            if (isAccessibleUser(email, USERS_READ)) {
                final var token = Objects.requireNonNull(response.getBody()).getToken();
                return restService.getUsersData(token);

            } else
                return new ApiResponse(
                        HttpStatus.FORBIDDEN,
                        new ResponseObject("Forbidden", "Method not allowed"));
        } else
            return new ApiResponse(
                    HttpStatus.CONFLICT,
                    new ResponseObject("This user has already Registered!", null));
    }

    public ApiResponse getSchools(String username, String password) {

        final var response = restService
                .getToken(
                        username,
                        password
                );

        if (isAccessibleUser(username, READ)) {
            final var token = Objects.requireNonNull(response.getBody()).getToken();
            return restService.getData(token);

        } else
            return new ApiResponse(
                    HttpStatus.FORBIDDEN,
                    new ResponseObject("Forbidden", "Method not allowed"));
    }

    public ApiResponse getSchoolsWithRegister(String fullName, String email, String password) {

        final var exists = existUser(email);
        if (!exists) {

            User user = User.builder()
                    .fullName(fullName)
                    .email(email)
                    .password(password)
                    .build();

            final var response = restService.register(user);
            final var userEmail = Objects.requireNonNull(response.getBody()).getUsername();

            if (isAccessibleUser(userEmail, READ)) {
                final var token = Objects.requireNonNull(response.getBody()).getToken();
                return restService.getData(token);

            } else
                return new ApiResponse(
                        HttpStatus.FORBIDDEN,
                        new ResponseObject("Forbidden", "Method not allowed"));
        } else
            return new ApiResponse(
                    HttpStatus.CONFLICT,
                    new ResponseObject("This user has already Registered!", null));
    }

    public boolean isAccessibleUser(String email, Permission permission) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        NotFoundException::new
                );

        boolean isAccess = false;
        final var permissions = user.getRoles().getPermissions();

        for (Permission permission1 : permissions) {
            if (permission1.equals(permission)) {
                isAccess = true;
                break;
            }
        }

        return isAccess;
    }

    public boolean existUser(String username) {
        return userRepository.existsByEmail(username);
    }
}
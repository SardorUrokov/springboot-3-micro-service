package com.mkb.controller;

import com.mkb.entity.User;
import com.mkb.response.ApiResponse;
import com.mkb.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/library")
@RequiredArgsConstructor
@RestController
@Slf4j
public class LibraryController {

    private final LibraryService libraryService;

    public record SchoolDTO(String email, String name) {
    }

    @GetMapping("/schools")
    public ResponseEntity<?> getSchoolList(@RequestParam(value = "fullName", required = false) String fullName,
                                           @RequestParam(value = "email", required = false) String email,
                                           @RequestParam(value = "username", required = false) String username,
                                           @RequestParam(value = "password") String password
    ) {
        if (username == null) {
            var response = libraryService
                    .getSchoolsWithRegister(fullName, email, password);

            return ResponseEntity
                    .status(response.getHttpStatus())
                    .body(response);
        } else {
            final var response = libraryService
                    .getSchools(username, password);

            return ResponseEntity
                    .status(response.getHttpStatus())
                    .body(response);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(value = "fullName", required = false) String fullName,
                                      @RequestParam(value = "email", required = false) String email,
                                      @RequestParam(value = "username", required = false) String username,
                                      @RequestParam(value = "password") String password
    ) {
        ApiResponse response;
        if (username == null) {
            response = libraryService
                    .getUsersDataWithRegister(fullName, email, password);

        } else {
            response = libraryService
                    .getUsersData(username, password);

        }
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response.getPayload());
    }

    @PostMapping("/saveSchool")
    public ResponseEntity<?> saveSchool(@RequestBody SchoolDTO schoolDTO,
                                        @RequestParam(value = "fullName", required = false) String fullName,
                                        @RequestParam(value = "email", required = false) String email,
                                        @RequestParam(value = "username", required = false) String username,
                                        @RequestParam(value = "password") String password
    ) {

        if (username == null) {

            User user = User.builder()
                    .fullName(fullName)
                    .email(email)
                    .password(password)
                    .build();

            final var response = libraryService.saveSchoolWithRegister(schoolDTO, user);
            return ResponseEntity
                    .status(response.getHttpStatus())
                    .body(response.getPayload());

        } else {

            LibraryService.AuthRequestDTO authRequestDTO = new LibraryService.AuthRequestDTO(username, password);

            final var response = libraryService.saveSchoolWithAuthenticate(schoolDTO, authRequestDTO);
            return ResponseEntity
                    .status(response.getHttpStatus())
                    .body(response.getPayload());
        }
    }
}
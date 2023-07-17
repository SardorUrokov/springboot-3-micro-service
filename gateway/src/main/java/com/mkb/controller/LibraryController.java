package com.mkb.controller;

import com.mkb.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService libraryService;

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
        if (username == null)
            return ResponseEntity
                    .ok(libraryService
                            .getUsersDataWithRegister(fullName, email, password)
                    );

        else
            return ResponseEntity
                    .ok(libraryService
                            .getUsersData(username, password)
                    );
    }
}
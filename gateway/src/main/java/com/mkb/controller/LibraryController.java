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
    public ResponseEntity<?> getSchoolList(@RequestBody LibraryService.AuthRequestDTO requestDTO){
        final var response = libraryService.getSchools(requestDTO);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.ok(libraryService.getUsersData(username, password));
    }
}
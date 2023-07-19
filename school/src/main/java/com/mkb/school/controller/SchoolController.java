package com.mkb.school.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.mkb.school.entity.School;
import com.mkb.school.service.SchoolService;
import com.mkb.school.response.FullSchoolResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schools")
public class SchoolController {

    private final SchoolService service;

    public record SchoolDTO(String email, String name){}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasAuthority('admin:create')")
    public ResponseEntity<?> save(@RequestBody SchoolDTO schoolDTO) {

        final var savedSchool = service.saveSchool(schoolDTO);
        log.info("School is saved! -> {}", savedSchool.getPayload().getObject());

        return ResponseEntity
                .status(savedSchool.getHttpStatus())
                .body(savedSchool.getPayload());
    }

    @GetMapping("/")
    @PreAuthorize(value = "hasAnyAuthority('read')")
    public ResponseEntity<?> findAllSchools() {

        final var response = service.findAllSchools();
        log.info("School List! -> {}", response.getPayload().getObject());

        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @GetMapping("/users")
    @PreAuthorize(value = "hasAnyAuthority('users:read')")
    public ResponseEntity<?> findUsers() {

        final var response = service.getUsers();
        log.info("Users List -> {}", response.getPayload().getObject());

        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @GetMapping("/with-students/{school-id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<FullSchoolResponse> findAllSchools(
            @PathVariable("school-id") Integer schoolId) {

        final var schoolsWithStudents = service.findSchoolsWithStudents(schoolId);
        log.info("School With Students! -> {}", schoolsWithStudents);

        return ResponseEntity.ok(schoolsWithStudents);
    }
}
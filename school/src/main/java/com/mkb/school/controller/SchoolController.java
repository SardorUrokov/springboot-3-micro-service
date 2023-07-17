package com.mkb.school.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.mkb.school.entity.School;
import com.mkb.school.service.SchoolService;
import com.mkb.school.response.FullSchoolResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody School school) {

        final var savedSchool = service.saveSchool(school);
        log.info("School is saved! -> {}", savedSchool.getPayload().getObject());
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllSchools() {

        final var response = service.findAllSchools();
        log.info("School List! -> {}", response.getPayload().getObject());

        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }


    @GetMapping("/users")
    public ResponseEntity<?> findUsers(){

        final var response = service.getUsers();
        log.info("Users List -> {}", response.getPayload().getObject());

        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @GetMapping("/with-students/{school-id}")
    public ResponseEntity<FullSchoolResponse> findAllSchools(
            @PathVariable("school-id") Integer schoolId) {

        final var schoolsWithStudents = service.findSchoolsWithStudents(schoolId);
        log.info("School With Students! -> {}", schoolsWithStudents);

        return ResponseEntity.ok(schoolsWithStudents);
    }
}
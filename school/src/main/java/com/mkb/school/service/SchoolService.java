package com.mkb.school.service;

import com.mkb.school.controller.SchoolController;
import com.mkb.school.entity.School;
import com.mkb.school.response.ApiResponse;
import com.mkb.school.response.ResponseObject;
import com.mkb.school.repository.UserRepository;
import com.mkb.school.feighnClient.StudentClient;
import com.mkb.school.response.FullSchoolResponse;
import com.mkb.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final StudentClient client;
    private final SchoolRepository repository;
    private final UserRepository userRepository;

    public ApiResponse saveSchool(SchoolController.SchoolDTO schoolDTO) {

        School newSchool = School.builder()
                .email(schoolDTO.email())
                .name(schoolDTO.name())
                .build();
        final var saved = repository.save(newSchool);

        return ApiResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .payload(ResponseObject.builder()
                        .message("School is saved!")
                        .object(saved)
                        .build())
                .build();
    }

    public ApiResponse findAllSchools() {

        final var schoolList = repository.findAll();

        return ApiResponse.builder()
                .httpStatus(HttpStatus.OK)
                .payload(ResponseObject.builder()
                        .message("Schools List")
                        .object(schoolList)
                        .build())
                .build();
    }

    public ApiResponse getUsers() {

        final var userList = userRepository.findAll();

        return ApiResponse.builder()
                .httpStatus(HttpStatus.OK)
                .payload(ResponseObject.builder()
                        .message("Users List")
                        .object(userList)
                        .build())
                .build();
    }

    public FullSchoolResponse findSchoolsWithStudents(Integer schoolId) {

        var school = repository.findById(schoolId)
                .orElse(School.builder()
                        .name("NOT_FOUND")
                        .email("NOT_FOUND")
                        .build()
                );

        var students = client.findAllStudentsBySchool(schoolId);
        return FullSchoolResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
package com.mkb.school.service;

import com.mkb.school.response.FullSchoolResponse;
import com.mkb.school.repository.SchoolRepository;
import com.mkb.school.feighnClient.StudentClient;
import com.mkb.school.entity.School;
import com.mkb.school.response.ApiResponse;
import com.mkb.school.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository repository;
    private final StudentClient client;

    public void saveSchool(School school) {
        repository.save(school);
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
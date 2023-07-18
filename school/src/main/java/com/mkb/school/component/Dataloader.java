package com.mkb.school.component;

import com.mkb.school.entity.User;
import com.mkb.school.entity.School;
import com.mkb.school.entity.enums.Roles;
import com.mkb.school.repository.UserRepository;
import com.mkb.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class Dataloader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SchoolRepository schoolRepository;

    @Override
    public void run(String... args) throws Exception {

        final var schoolList = schoolRepository.findAll();
        final var userList = userRepository.findAll();

        if (schoolList.isEmpty()) {
            schoolRepository.save(new School(1, "1-maktab", "N1_maktab@gmail.com"));
            schoolRepository.save(new School(2, "2-maktab", "N2_maktab@gmail.com"));
            schoolRepository.save(new School(3, "3-maktab", "N3_maktab@gmail.com"));
        }

        if (userList.isEmpty()) {
            userRepository.save(new User("AAA", "1@gmail.com", passwordEncoder.encode("123"), Roles.ADMIN));
        }
    }
}
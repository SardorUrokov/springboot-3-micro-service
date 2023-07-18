package com.mkb.component;

import com.mkb.entity.User;
import com.mkb.entity.enums.Roles;
import com.mkb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class Dataloader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        final var userList = userRepository.findAll();

        if (userList.isEmpty()) {
            userRepository.save(
                    new User(
                            "AAA",
                            "1@gmail.com",
                            passwordEncoder.encode("123"),
                            Roles.ADMIN
                    )
            );
        }
    }
}

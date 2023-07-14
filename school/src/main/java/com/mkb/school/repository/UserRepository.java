package com.mkb.school.repository;

import java.util.Optional;
import com.mkb.school.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail (String email);
}

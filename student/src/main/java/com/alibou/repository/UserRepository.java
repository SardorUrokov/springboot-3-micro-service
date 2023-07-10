package com.alibou.repository;

import com.alibou.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String username);

    boolean existsByEmail (String email);
}
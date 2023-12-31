package com.mkb.repository;

import com.mkb.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByGeneratedToken (String generatedToken);
}
package com.mkb.school.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String generatedToken;

    @OneToOne
    User user;

    boolean revoked, expired;

    Date createdAt;

    public Token(String generatedToken, User user, boolean revoked, boolean expired, Date createdAt) {
        this.generatedToken = generatedToken;
        this.user = user;
        this.revoked = revoked;
        this.expired = expired;
        this.createdAt = createdAt;
    }
}
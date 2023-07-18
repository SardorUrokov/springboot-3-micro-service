package com.mkb.school.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String email;

    public School(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
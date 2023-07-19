package com.mkb.school.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USERS_READ("users:read"),

    READ_ONLY("user:read"),

    READ("admin:read"),

    UPDATE("admin:update"),

    CREATE("admin:create"),

    DELETE("admin:delete");

    @Getter
    private final String permission;
}
package com.mkb.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USERS_READ("users:read"),

    READ("read"),

    UPDATE("admin:update"),

    CREATE("admin:create"),

    DELETE("admin:delete");

    @Getter
    private final String permission;
}
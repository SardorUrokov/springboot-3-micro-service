package com.mkb.school.entity.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mkb.school.entity.enums.Permission.*;

@RequiredArgsConstructor
public enum Roles {

    USER(
            Set.of(
                    READ_ONLY
            )
    ),

    ADMIN(
            Set.of(
                    READ,
                    CREATE,
                    UPDATE,
                    DELETE,
                    READ_USERS
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission ->
                        new SimpleGrantedAuthority(permission.getPermission()
                        ))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
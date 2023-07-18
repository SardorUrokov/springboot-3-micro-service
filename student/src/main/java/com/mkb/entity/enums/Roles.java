package com.mkb.entity.enums;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import static com.mkb.entity.enums.Permission.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
                    READ_USERS,
                    CREATE,
                    UPDATE,
                    DELETE
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
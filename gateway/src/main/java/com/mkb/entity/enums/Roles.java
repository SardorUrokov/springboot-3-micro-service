package com.mkb.entity.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import static com.mkb.entity.enums.Permission.*;


@RequiredArgsConstructor
public enum Roles {

    USER(
            Set.of(
                    READ
            )
    ),

    ADMIN(
            Set.of(
                    READ,
                    CREATE,
                    UPDATE,
                    DELETE,
                    USERS_READ
            )
    );

    @Getter
    private final Set<Permission> permissions;

//    public Set<SimpleGrantedAuthority> getAuthorities() {
//        var authorities = getPermissions()
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toSet());
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//        return authorities;
//    }
}
package com.homesetu.homesetu_backend.security;

import com.homesetu.homesetu_backend.common.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPrincipal {

    private Long userId;
    private Role role;
}

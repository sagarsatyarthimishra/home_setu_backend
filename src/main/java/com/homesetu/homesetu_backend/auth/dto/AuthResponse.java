package com.homesetu.homesetu_backend.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String message;
    private String role;
}

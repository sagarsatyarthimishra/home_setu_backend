package com.homesetu.homesetu_backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserProfileRequest {
    private String fullName;
    private String email;
}

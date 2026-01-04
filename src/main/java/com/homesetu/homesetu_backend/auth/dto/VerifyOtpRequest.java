package com.homesetu.homesetu_backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerifyOtpRequest {
    @NotBlank
    private String mobile;

    @NotBlank
    private String otp;
}

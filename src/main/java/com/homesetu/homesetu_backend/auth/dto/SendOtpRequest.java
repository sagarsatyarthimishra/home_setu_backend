package com.homesetu.homesetu_backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SendOtpRequest {
    @NotBlank
    private String mobile;
}

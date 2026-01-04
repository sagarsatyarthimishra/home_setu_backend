package com.homesetu.homesetu_backend.auth.controller;

import com.homesetu.homesetu_backend.auth.dto.*;
import com.homesetu.homesetu_backend.auth.service.AuthService;
import com.homesetu.homesetu_backend.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/send-otp")
    public ApiResponse<?> sendOtp(@Valid @RequestBody SendOtpRequest req) {
        authService.sendOtp(req.getMobile());
        return ApiResponse.success("OTP sent successfully", null);
    }

    @PostMapping("/verify-otp")
    public ApiResponse<AuthResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest req) {
        return ApiResponse.success(
                "OTP verified",
                authService.verifyOtpAndLogin(req.getMobile(), req.getOtp())
        );
    }
}

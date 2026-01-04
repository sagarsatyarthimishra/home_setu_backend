package com.homesetu.homesetu_backend.auth.service;

import com.homesetu.homesetu_backend.auth.dto.AuthResponse;
import com.homesetu.homesetu_backend.auth.entity.AppUser;
import com.homesetu.homesetu_backend.auth.repository.AppUserRepository;
import com.homesetu.homesetu_backend.common.constants.Role;
import com.homesetu.homesetu_backend.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepo;
    private final OtpService otpService;
    private  final JwtUtil jwtUtil;

    public void sendOtp(String mobile) {
        otpService.sendOtp(mobile);
    }

    public AuthResponse verifyOtpAndLogin(String mobile, String otp) {

        otpService.verifyOtp(mobile, otp);

        AppUser user = userRepo.findByMobile(mobile)
                .orElseGet(() -> userRepo.save(
                        AppUser.builder()
                                .mobile(mobile)
                                .role(Role.USER)
                                .build()
                ));

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .message("Login successful")
                .role(user.getRole().name())
                .token(token)
                .build();
    }
}

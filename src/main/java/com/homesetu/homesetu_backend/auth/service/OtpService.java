package com.homesetu.homesetu_backend.auth.service;

import com.homesetu.homesetu_backend.auth.entity.OtpVerification;
import com.homesetu.homesetu_backend.auth.repository.OtpVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpVerificationRepository otpRepo;

    public void sendOtp(String mobile) {

        // 🔁 delete old OTP (if exists)
        otpRepo.deleteByMobile(mobile);

        String otp = generateOtp();

        OtpVerification verification = OtpVerification.builder()
                .mobile(mobile)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        otpRepo.save(verification);

        // TEMP: SMS integration later
        System.out.println("OTP for " + mobile + " = " + otp);
    }

    public void verifyOtp(String mobile, String otp) {

        OtpVerification record = otpRepo.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (record.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpRepo.delete(record); // 🔥 cleanup
            throw new RuntimeException("OTP expired");
        }

        if (!record.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        // OTP used → delete immediately
        otpRepo.delete(record);
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}

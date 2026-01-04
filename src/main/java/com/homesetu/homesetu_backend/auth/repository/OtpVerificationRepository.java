package com.homesetu.homesetu_backend.auth.repository;

import com.homesetu.homesetu_backend.auth.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findByMobile(String mobile);

    void deleteByMobile(String mobile);
}


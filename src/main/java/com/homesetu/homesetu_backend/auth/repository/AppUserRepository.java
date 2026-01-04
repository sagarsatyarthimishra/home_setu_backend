package com.homesetu.homesetu_backend.auth.repository;

import com.homesetu.homesetu_backend.auth.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByMobile(String mobile);
}

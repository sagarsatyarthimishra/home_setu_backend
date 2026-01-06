package com.homesetu.homesetu_backend.auth.repository;

import com.homesetu.homesetu_backend.auth.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}


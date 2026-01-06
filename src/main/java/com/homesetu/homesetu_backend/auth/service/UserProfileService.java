package com.homesetu.homesetu_backend.auth.service;

import com.homesetu.homesetu_backend.auth.dto.UserProfileRequest;
import com.homesetu.homesetu_backend.auth.entity.UserProfile;
import com.homesetu.homesetu_backend.auth.repository.UserProfileRepository;
import com.homesetu.homesetu_backend.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository profileRepo;

    public UserProfile getOrCreate(Long userId) {
        return profileRepo.findById(userId)
                .orElseGet(() -> profileRepo.save(
                        UserProfile.builder().userId(userId).build()
                ));
    }

    public UserProfile updateProfile(Long userId, UserProfileRequest req) {

        UserProfile profile = getOrCreate(userId);
        profile.setFullName(req.getFullName());
        profile.setEmail(req.getEmail());

        return profileRepo.save(profile);
    }

    public UserProfile getProfile(Long userId) {
        return profileRepo.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("User profile not found"));
    }

}


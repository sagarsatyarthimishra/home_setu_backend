package com.homesetu.homesetu_backend.auth.controller;

import com.homesetu.homesetu_backend.auth.dto.AddressRequest;
import com.homesetu.homesetu_backend.auth.dto.UserProfileRequest;
import com.homesetu.homesetu_backend.auth.service.LocationService;
import com.homesetu.homesetu_backend.auth.service.UserAddressService;
import com.homesetu.homesetu_backend.auth.service.UserProfileService;
import com.homesetu.homesetu_backend.common.response.ApiResponse;
import com.homesetu.homesetu_backend.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService profileService;
    private final UserAddressService addressService;
    private final LocationService locationService;

    @PutMapping("/profile")
    public ApiResponse<?> updateProfile(
            @RequestBody UserProfileRequest req,
            Authentication auth
    ) {
        Long userId = ((UserPrincipal) auth.getPrincipal()).getUserId();
        return ApiResponse.success(
                "Profile updated",
                profileService.updateProfile(userId, req)
        );
    }

    @PostMapping("/address")
    public ApiResponse<?> addAddress(
            @RequestBody AddressRequest req,
            Authentication auth
    ) {
        Long userId = ((UserPrincipal) auth.getPrincipal()).getUserId();
        return ApiResponse.success(
                "Address added",
                addressService.addAddress(userId, req)
        );
    }

    @GetMapping("/address")
    public ApiResponse<?> listAddresses(Authentication auth) {
        Long userId = ((UserPrincipal) auth.getPrincipal()).getUserId();
        return ApiResponse.success(
                "Address list",
                addressService.listAddresses(userId)
        );
    }

    @PutMapping("/address/{id}")
    public ApiResponse<?> updateAddress(
            @PathVariable Long id,
            @RequestBody AddressRequest req,
            Authentication auth
    ) {
        Long userId = ((UserPrincipal) auth.getPrincipal()).getUserId();
        return ApiResponse.success(
                "Address updated",
                addressService.updateAddress(id, userId, req)
        );
    }

    @DeleteMapping("/address/{id}")
    public ApiResponse<?> deleteAddress(
            @PathVariable Long id,
            Authentication auth
    ) {
        Long userId = ((UserPrincipal) auth.getPrincipal()).getUserId();
        addressService.deleteAddress(id, userId);
        return ApiResponse.success("Address deleted", null);
    }

    @GetMapping("/location/reverse")
    public ApiResponse<?> reverseLocation(
            @RequestParam Double lat,
            @RequestParam Double lng
    ) {
        return ApiResponse.success(
                "Location fetched",
                locationService.reverseGeocode(lat, lng)
        );
    }

    @GetMapping("/profile")
    public ApiResponse<?> getProfile(Authentication auth) {

        Long userId = ((UserPrincipal) auth.getPrincipal()).getUserId();

        return ApiResponse.success(
                "Profile fetched",
                profileService.getProfile(userId)
        );
    }
}


package com.homesetu.homesetu_backend.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationResponse {

    private String displayName;
    private String city;
    private String state;
    private String country;
    private String pincode;

    private Double latitude;
    private Double longitude;
}


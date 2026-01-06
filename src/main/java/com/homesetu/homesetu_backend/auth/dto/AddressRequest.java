package com.homesetu.homesetu_backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

    private String houseNumber;
    private String street;
    private String landmark;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private boolean defaultAddress;
}

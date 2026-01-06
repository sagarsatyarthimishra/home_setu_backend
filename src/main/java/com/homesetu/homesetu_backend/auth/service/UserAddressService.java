package com.homesetu.homesetu_backend.auth.service;

import com.homesetu.homesetu_backend.auth.dto.AddressRequest;
import com.homesetu.homesetu_backend.auth.entity.UserAddress;
import com.homesetu.homesetu_backend.auth.entity.UserProfile;
import com.homesetu.homesetu_backend.auth.repository.UserAddressRepository;
import com.homesetu.homesetu_backend.auth.repository.UserProfileRepository;
import com.homesetu.homesetu_backend.common.exception.NotFoundException;
import com.homesetu.homesetu_backend.common.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressService {

    private final UserProfileRepository profileRepo;
    private final UserAddressRepository addressRepo;
    private final LocationService locationService;

    public UserAddress addAddress(Long userId, AddressRequest req) {

        UserProfile profile = profileRepo.findById(userId)
                .orElseGet(() -> profileRepo.save(
                        UserProfile.builder().userId(userId).build()
                ));

        String fullAddress = String.join(", ",
                req.getHouseNumber(),
                req.getStreet(),
                req.getCity(),
                req.getState(),
                req.getPincode(),
                req.getCountry()
        );

        double[] latLng = locationService.getLatLngFromAddress(fullAddress);

        if (req.isDefaultAddress()) {
            addressRepo.findByUserProfile_UserId(userId)
                    .forEach(a -> {
                        a.setDefaultAddress(false);
                        addressRepo.save(a);
                    });
        }

        UserAddress address = UserAddress.builder()
                .houseNumber(req.getHouseNumber())
                .street(req.getStreet())
                .landmark(req.getLandmark())
                .city(req.getCity())
                .state(req.getState())
                .country(req.getCountry())
                .pincode(req.getPincode())
                .latitude(latLng[0])
                .longitude(latLng[1])
                .defaultAddress(req.isDefaultAddress())
                .userProfile(profile)
                .build();

        return addressRepo.save(address);
    }

    public List<UserAddress> listAddresses(Long userId) {
        return addressRepo.findByUserProfile_UserId(userId);
    }

    public UserAddress updateAddress(Long id, Long userId, AddressRequest req) {

        UserAddress address = addressRepo.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Address not found"));

        if (!address.getUserProfile().getUserId().equals(userId)) {
            throw new UnauthorizedException("You cannot update this address");
        }

        String fullAddress = req.getHouseNumber() + ", " +
                req.getCity() + ", " + req.getState();

        double[] latLng = locationService.getLatLngFromAddress(fullAddress);

        address.setHouseNumber(req.getHouseNumber());
        address.setStreet(req.getStreet());
        address.setLandmark(req.getLandmark());
        address.setCity(req.getCity());
        address.setState(req.getState());
        address.setCountry(req.getCountry());
        address.setPincode(req.getPincode());
        address.setLatitude(latLng[0]);
        address.setLongitude(latLng[1]);

        return addressRepo.save(address);
    }

    public void deleteAddress(Long id, Long userId) {

        UserAddress address = addressRepo.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Address not found"));

        if (!address.getUserProfile().getUserId().equals(userId)) {
            throw new UnauthorizedException("You cannot delete this address");
        }

        addressRepo.delete(address);
    }
}

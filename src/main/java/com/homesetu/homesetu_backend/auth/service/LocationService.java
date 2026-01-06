package com.homesetu.homesetu_backend.auth.service;

import com.homesetu.homesetu_backend.auth.dto.LocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final RestTemplate restTemplate = new RestTemplate();

    // 🔹 Forward geocoding (address → lat/lng)
    public double[] getLatLngFromAddress(String address) {

        String url = "https://nominatim.openstreetmap.org/search"
                + "?format=json"
                + "&q=" + address
                + "&limit=1";

        List<Map<String, Object>> response =
                restTemplate.getForObject(url, List.class);

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("Unable to fetch location from address");
        }

        Map<String, Object> result = response.get(0);

        return new double[]{
                Double.parseDouble(result.get("lat").toString()),
                Double.parseDouble(result.get("lon").toString())
        };
    }

    // 🔹 Reverse geocoding (lat/lng → readable)
    public LocationResponse reverseGeocode(Double lat, Double lng) {
        String url = "https://nominatim.openstreetmap.org/reverse"
                + "?format=json"
                + "&lat=" + lat
                + "&lon=" + lng
                + "&addressdetails=1";

        Map response = restTemplate.getForObject(url, Map.class);
        Map address = (Map) response.get("address");

        return LocationResponse.builder()
                .displayName((String) response.get("display_name"))
                .city((String) address.get("city"))
                .state((String) address.get("state"))
                .country((String) address.get("country"))
                .pincode((String) address.get("postcode"))
                .latitude(lat)
                .longitude(lng)
                .build();
    }
}

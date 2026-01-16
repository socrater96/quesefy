package com.boveda.quesefy.domain.dto;

public record LocationDto(
        String address,
        String city,
        String province,
        String zipcode,
        String country,

        Double latitude,
        Double longitude
) {
}

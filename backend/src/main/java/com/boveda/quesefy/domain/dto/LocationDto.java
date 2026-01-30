package com.boveda.quesefy.domain.dto;

import jakarta.validation.constraints.NotNull;

public record LocationDto(
        @NotNull String address,
        @NotNull String city,
        @NotNull String province,
        @NotNull String zipcode,
        @NotNull String country,

        @NotNull Double latitude,
        @NotNull Double longitude
) {
}

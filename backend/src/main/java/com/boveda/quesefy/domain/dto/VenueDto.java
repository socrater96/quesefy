package com.boveda.quesefy.domain.dto;

import com.boveda.quesefy.domain.entity.VenueType;

import java.util.UUID;

public record VenueDto(
        UUID id,
        String name,
        VenueType venueType,
        LocationDto location
) {
}

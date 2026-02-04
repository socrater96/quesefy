package com.boveda.quesefy.domain.dto;

import com.boveda.quesefy.domain.entity.VenueType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record VenueDto(
        @Schema(
                description = "Venue ID",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,

        @Schema(
                description = "Venue's name"
        )
        String name,

        @Schema(
                description = "Venue's type"
        )
        VenueType venueType,
        @Schema(
                description = "Venue's location"
        )
        LocationDto location
) {
}

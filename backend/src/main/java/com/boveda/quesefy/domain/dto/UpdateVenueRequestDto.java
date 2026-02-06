package com.boveda.quesefy.domain.dto;

import com.boveda.quesefy.domain.entity.VenueType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdateVenueRequestDto(
        @Nullable
        String name,

        @Nullable
        VenueType venueType,

        @Nullable
        LocationDto location
) {

}

package com.boveda.quesefy.domain.dto;

import com.boveda.quesefy.domain.entity.VenueType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateVenueRequestDto(

        @NotBlank(message = ERROR_MESSAGE_TITLE_LENGTH)
        @Length(min = 3, max = 30)
        String name,

        @NotNull(message = ERROR_MESSAGE_VENUE_TYPE)
        VenueType venueType,

        @NotNull(message = ERROR_MESSAGE_LOCATION)
        LocationDto location
) {
    private static final String ERROR_MESSAGE_TITLE_LENGTH =
            "Title must be between 3 and 30 characters";
    private static final String ERROR_MESSAGE_VENUE_TYPE =
            "Venue type must be provided";
    private static final String ERROR_MESSAGE_LOCATION =
            "Location must be provided";
}

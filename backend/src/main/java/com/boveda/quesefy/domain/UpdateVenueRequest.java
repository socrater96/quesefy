package com.boveda.quesefy.domain;

import com.boveda.quesefy.domain.entity.Location;
import com.boveda.quesefy.domain.entity.VenueType;

public record UpdateVenueRequest(
        String name,
        VenueType venueType,
        Location location
) {
}

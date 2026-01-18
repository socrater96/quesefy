package com.boveda.quesefy.domain;

import com.boveda.quesefy.domain.entity.Location;
import com.boveda.quesefy.domain.entity.Venue;
import com.boveda.quesefy.domain.entity.VenueType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VenueTest {

    @Test
    void shouldCreateVenueWithLocation() {
        Location location = new Location(
                "Orzán, 18",
                "A Coruña",
                "A Coruña",
                "15003",
                "Spain",
                40.4168,
                -3.7038
        );
        Venue venue = new Venue(
                UUID.randomUUID(),
                "Folks",
                VenueType.NIGHT_CLUB,
                location
        );

        assertThat(venue.getName()).isEqualTo("Folks");
        assertThat(venue.getLocation()).isEqualTo(location);
        assertThat(venue.getVenueType()).isEqualTo(VenueType.NIGHT_CLUB);
    }
}


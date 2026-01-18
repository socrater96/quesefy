package com.boveda.quesefy.domain;

import com.boveda.quesefy.domain.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EventTest {

    @Test
    void shouldAssignVenueToEvent() {
        Event event = new Event(
                UUID.randomUUID(),
                "Concierto",
                "Rock",
                LocalDateTime.now().plusDays(1),
                EventType.CONCERT,
                EventStatus.DUE
        );

        Venue venue = new Venue(
                UUID.randomUUID(),
                "Sala Riviera",
                VenueType.MUSIC_VENUE,
                new Location("Av. Valladolid",
                        "Madrid",
                        "Spain",
                        "28008",
                        "Spain",
                        40.4168,
                        -3.7038)
        );

        event.assignVenue(venue);

        assertThat(event.getVenue()).isEqualTo(venue);
    }

    @Test
    void shouldRemoveVenueFromEvent() {
        Event event = new Event(
                UUID.randomUUID(),
                "Concierto",
                "Rock",
                LocalDateTime.now().plusDays(1),
                EventType.CONCERT,
                EventStatus.DUE
        );

        Venue venue = new Venue(
                UUID.randomUUID(),
                "Sala Riviera",
                VenueType.MUSIC_VENUE,
                new Location("Av. Valladolid",
                        "Madrid",
                        "Spain",
                        "28008",
                        "Spain",
                        40.4168,
                        -3.7038)
        );

        event.assignVenue(venue);
        event.removeVenue();

        assertThat(event.getVenue()).isNull();
    }
}


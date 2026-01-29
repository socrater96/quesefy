package com.boveda.quesefy.domain;

import com.boveda.quesefy.domain.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EventTest {

    @Test
    void shouldAssignVenueToEvent() {
        Event event = Event.builder()
                .id(UUID.randomUUID())
                .title("title")
                .description("Rock")
                .date(LocalDateTime.now().plusDays(1))
                .type(EventType.CONCERT)
                .build();

        Venue venue = Venue.builder()
                .id(UUID.randomUUID())
                .name("Sala Riviera")
                .venueType(VenueType.MUSIC_VENUE)
                .location(Location.builder()
                        .address("Av. Valladolid")
                        .city("Madrid")
                        .province("Madrid")
                        .zipcode("28008")
                        .country("Spain")
                        .latitude(40.4168)
                        .longitude(-3.7038)
                        .build())
                .build();

        event.assignVenue(venue);

        assertThat(event.getVenue()).isEqualTo(venue);
    }

    @Test
    void shouldRemoveVenueFromEvent() {
        Event event = Event.builder()
                .id(UUID.randomUUID())
                .title("title")
                .description("Rock")
                .date(LocalDateTime.now().plusDays(1))
                .type(EventType.CONCERT)
                .build();

        Venue venue = Venue.builder()
                .id(UUID.randomUUID())
                .name("Sala Riviera")
                .venueType(VenueType.MUSIC_VENUE)
                .location(Location.builder()
                        .address("Av. Valladolid")
                        .city("Madrid")
                        .province("Madrid")
                        .zipcode("28008")
                        .country("Spain")
                        .latitude(40.4168)
                        .longitude(-3.7038)
                        .build())
                .build();

        event.assignVenue(venue);
        event.removeVenue();

        assertThat(event.getVenue()).isNull();
    }
}


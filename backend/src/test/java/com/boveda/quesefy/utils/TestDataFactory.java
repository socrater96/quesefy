package com.boveda.quesefy.utils;

import com.boveda.quesefy.domain.CreateEventRequest;
import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.UpdateEventRequest;
import com.boveda.quesefy.domain.dto.EventDto;
import com.boveda.quesefy.domain.dto.LocationDto;
import com.boveda.quesefy.domain.dto.VenueDto;
import com.boveda.quesefy.domain.entity.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestDataFactory {

    private static final LocalDateTime VALID_DATE =
            LocalDateTime.of(2030, 1, 1, 20, 0);
    public static final String EVENT_TITLE = "DJ Chvrly";
    public static final String EVENT_DESCRIPTION = "DJ Chvrly amenizará esta noche de jarana en el Folks";
    public static final EventType EVENT_TYPE = EventType.CONCERT;

    public static final String VENUE_NAME = "Folks";
    public static final VenueType VENUE_TYPE = VenueType.NIGHT_CLUB;

    public static final String LOCATION_ADDRESS = "Orzán, 19";
    public static final String LOCATION_CITY = "A Coruña";
    public static final String LOCATION_PROVINCE = "A Coruña";
    public static final String LOCATION_ZIP_CODE = "15001";
    public static final String LOCATION_COUNTRY = "Spain";
    public static final double LOCATION_LATITUDE = 43.371909;
    public static final double LOCATION_LONGITUDE = -8.400471;

    /**************REQUESTS**************************/

    public static CreateEventRequest createEventRequest() {
        return new CreateEventRequest(
                EVENT_TITLE,
                EVENT_DESCRIPTION,
                VALID_DATE,
                EVENT_TYPE
        );

    }

    public static UpdateEventRequest createUpdateEventRequest() {
        return new UpdateEventRequest(
                EVENT_TITLE,
                EVENT_DESCRIPTION,
                VALID_DATE,
                EVENT_TYPE,
                EventStatus.COMPLETED
        );

    }

    public static CreateVenueRequest createCreateVenueRequest() {
        return new CreateVenueRequest(
                VENUE_NAME,
                VENUE_TYPE,
                createLocation()
        );
    }
    /**************ENTITIES**************************/

    public static Event createEvent(UUID eventId) {
        return Event.builder()
                .id(eventId)
                .title(EVENT_TITLE)
                .description(EVENT_DESCRIPTION)
                .date(VALID_DATE)
                .type(EVENT_TYPE)
                .build();

    }

    public static Event createEventWithVenue(UUID eventId, Venue venue) {
        return Event.builder()
                .id(eventId)
                .title(EVENT_TITLE)
                .description(EVENT_DESCRIPTION)
                .date(VALID_DATE)
                .type(EVENT_TYPE)
                .venue(venue)
                .build();
    }

    public static Location createLocation() {
        return Location.builder()
                .address(LOCATION_ADDRESS)
                .city(LOCATION_CITY)
                .province(LOCATION_PROVINCE)
                .zipcode(LOCATION_ZIP_CODE)
                .country(LOCATION_COUNTRY)
                .latitude(LOCATION_LATITUDE)
                .longitude(LOCATION_LONGITUDE)
                .build();

    }

    public static Venue createVenue(UUID venueId) {
        return Venue.builder()
                .id(venueId)
                .name(VENUE_NAME)
                .venueType(VENUE_TYPE)
                .location(createLocation())
                .build();

    }

    /**************DTOS**************************/

    public static EventDto createEventDto(UUID eventId) {
        return new EventDto(
                eventId,
                EVENT_TITLE,
                EVENT_DESCRIPTION,
                VALID_DATE,
                EVENT_TYPE,
                EventStatus.DUE,
                null
        );

    }

    public static EventDto createEventDtoWithVenue(UUID eventId, VenueDto venue) {
        return new EventDto(
                eventId,
                EVENT_TITLE,
                EVENT_DESCRIPTION,
                VALID_DATE,
                EVENT_TYPE,
                EventStatus.DUE,
                venue
        );
    }

    public static LocationDto createLocationDto() {
        return new LocationDto(
                LOCATION_ADDRESS,
                LOCATION_CITY,
                LOCATION_PROVINCE,
                LOCATION_ZIP_CODE,
                LOCATION_COUNTRY,
                LOCATION_LATITUDE,
                LOCATION_LONGITUDE
        );
    }

    public static VenueDto createVenueDto(UUID venueId) {
        return new VenueDto(
                venueId,
                VENUE_NAME,
                VENUE_TYPE,
                createLocationDto()
        );
    }

}

package com.boveda.quesefy.domain;

import com.boveda.quesefy.domain.entity.EventType;
import com.boveda.quesefy.domain.entity.Venue;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateEventRequest(
        String title,
        String description,
        LocalDateTime date,
        EventType type,
        UUID venueId
) {

}

package com.boveda.quesefy.domain.dto;

import com.boveda.quesefy.domain.entity.EventStatus;
import com.boveda.quesefy.domain.entity.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(
        UUID id,
        String title,
        String description,
        LocalDateTime date,
        EventType type,
        EventStatus status,
        VenueDto venue
) {


}

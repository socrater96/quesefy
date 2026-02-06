package com.boveda.quesefy.domain;

import com.boveda.quesefy.domain.entity.EventStatus;
import com.boveda.quesefy.domain.entity.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateEventRequest(
        String title,
        String description,
        LocalDateTime date,
        EventType type,
        EventStatus status,
        UUID venueId
){
}

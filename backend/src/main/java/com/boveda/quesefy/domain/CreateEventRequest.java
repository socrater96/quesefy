package com.boveda.quesefy.domain;

import com.boveda.quesefy.domain.entity.EventType;

import java.time.LocalDateTime;

public record CreateEventRequest(
        String title,
        String description,
        LocalDateTime date,
        EventType type
) {

}

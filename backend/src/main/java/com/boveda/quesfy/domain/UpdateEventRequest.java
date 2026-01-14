package com.boveda.quesfy.domain;

import com.boveda.quesfy.domain.entity.EventStatus;
import com.boveda.quesfy.domain.entity.EventType;

import java.time.LocalDateTime;

public record UpdateEventRequest(
        String title,
        String description,
        LocalDateTime date,
        EventType type,
        EventStatus status
){
}

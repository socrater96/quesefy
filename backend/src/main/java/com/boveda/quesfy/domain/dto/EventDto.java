package com.boveda.quesfy.domain.dto;

import com.boveda.quesfy.domain.entity.EventStatus;
import com.boveda.quesfy.domain.entity.EventType;

import java.time.LocalDate;
import java.util.UUID;

public record EventDto(
        UUID id,
        String title,
        String description,
        LocalDate date,
        EventType eventType,
        EventStatus eventStatus
) {


}

package com.boveda.quesfy.domain;

import com.boveda.quesfy.domain.entity.EventStatus;
import com.boveda.quesfy.domain.entity.EventType;

import java.time.LocalDate;
import java.util.Date;

public record CreateEventRequest(
        String title,
        String description,
        LocalDate date,
        EventType type
) {

}

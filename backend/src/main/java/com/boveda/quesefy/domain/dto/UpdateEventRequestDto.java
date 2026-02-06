package com.boveda.quesefy.domain.dto;

import com.boveda.quesefy.domain.entity.EventStatus;
import com.boveda.quesefy.domain.entity.EventType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateEventRequestDto(
        @Nullable
        @Length(min = 3, max = 30, message = ERROR_MESSAGE_TITLE_LENGTH)
        String title,

        @Length(max = 1000, message = ERROR_MESSAGE_DESCRIPTION_LENGTH)
        @Nullable
        String description,

        @Future(message = ERROR_MESSAGE_DUE_DATE_FUTURE)
        @Nullable
        LocalDateTime date,

        @Nullable
        EventType type,

        @Nullable
        EventStatus status,

        @Nullable
        UUID venueId
) {
    private static final String ERROR_MESSAGE_TITLE_LENGTH =
            "Title must be between 3 and 30 characters";
    private static final String ERROR_MESSAGE_DESCRIPTION_LENGTH =
            "Description must be less than 1000 characters";
    private static final String ERROR_MESSAGE_DUE_DATE_FUTURE =
            "Event date must be in the future";
}
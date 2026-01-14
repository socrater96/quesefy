package com.boveda.quesfy.domain.dto;

import com.boveda.quesfy.domain.entity.EventStatus;
import com.boveda.quesfy.domain.entity.EventType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record UpdateEventRequestDto(
        @NotBlank(message = ERROR_MESSAGE_TITLE_LENGTH)
        @Length(min = 3, max = 30)
        String title,
        @Length(max = 1000, message = ERROR_MESSAGE_DESCRIPTION_LENGTH)
        @Nullable
        String description,
        @Future(message = ERROR_MESSAGE_DUE_DATE_FUTURE)
        @Nullable
        LocalDateTime date,
        @NotNull(message = ERROR_MESSAGE_PRIORITY)
        EventType type,
        @NotNull(message = ERROR_MESSAGE_STATUS)
        EventStatus status
) {
    private static final String ERROR_MESSAGE_TITLE_LENGTH =
            "Title must be between 3 and 30 characters";
    private static final String ERROR_MESSAGE_DESCRIPTION_LENGTH =
            "Description must be less than 1000 characters";
    private static final String ERROR_MESSAGE_DUE_DATE_FUTURE =
            "Event date must be in the future";
    private static final String ERROR_MESSAGE_PRIORITY =
            "Event priority must be provided";
    private static final String ERROR_MESSAGE_STATUS =
            "Event status must be provided";
}
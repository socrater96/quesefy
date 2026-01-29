package com.boveda.quesefy.domain.dto;

import com.boveda.quesefy.domain.entity.EventType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Request payload to create a new event")
public record CreateEventRequestDto(
        @Schema(
                description = "Event title",
                example = "Grizzly Bear concert"
        )
        @NotBlank(message = ERROR_MESSAGE_TITLE_LENGTH)
        @Length(min = 3, max = 30)
        String title,

        @Schema(
                description = "Event description",
                example = "Music concert from New York band Grizzly Bear"
        )
        @Length(max = 1000, message = ERROR_MESSAGE_DESCRIPTION_LENGTH)
        String description,

        @Schema(
                description = "Event date and time",
                example = "2080-06-15T20:00:00"
        )
        @Future(message = ERROR_MESSAGE_DUE_DATE_FUTURE)
        @NotNull
        LocalDateTime date,

        @Schema(
                description = "Type",
                example = "CONCERT"
        )
        @NotNull(message = ERROR_MESSAGE_PRIORITY)
        EventType type,

        @Schema(
                description = "Identifier of the venue where the event takes place. Optional.",
                example = "123e4567-e89b-12d3-a456-426614174000",
                nullable = true
        )
        UUID venueId

) {
    private static final String ERROR_MESSAGE_TITLE_LENGTH =
            "Title must be between 3 and 30 characters";
    private static final String ERROR_MESSAGE_DESCRIPTION_LENGTH =
            "Description must be less than 1000 characters";
    private static final String ERROR_MESSAGE_DUE_DATE_FUTURE =
            "Event date must be in the future";
    private static final String ERROR_MESSAGE_PRIORITY=
            "Event priority must be provided";

}

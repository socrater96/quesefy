package com.boveda.quesefy.domain.dto;

import com.boveda.quesefy.domain.entity.EventStatus;
import com.boveda.quesefy.domain.entity.EventType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(
        @Schema(
                description = "Event ID",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,

        @Schema(
                description = "Event title",
                example = "Grizzly Bear concert"
        )
        String title,

        @Schema(
                description = "Event description",
                example = "Music concert from New York band Grizzly Bear"
        )
        String description,

        @Schema(
                description = "Event date and time",
                example = "2080-06-15T20:00:00"
        )
        LocalDateTime date,

        @Schema(
                description = "Event type",
                example = "CONCERT"
        )
        EventType type,

        @Schema(
                description = "Event status",
                example = "DUE"
        )
        EventStatus status,

        @Schema(
                description = "Venue where the event is held"
        )
        VenueDto venue
) {


}

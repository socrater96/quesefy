package com.boveda.quesefy.mapper;

import com.boveda.quesefy.domain.CreateEventRequest;
import com.boveda.quesefy.domain.UpdateEventRequest;
import com.boveda.quesefy.domain.dto.CreateEventRequestDto;
import com.boveda.quesefy.domain.dto.EventDto;
import com.boveda.quesefy.domain.dto.UpdateEventRequestDto;
import com.boveda.quesefy.domain.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventMapper {

    CreateEventRequest fromDto(CreateEventRequestDto requestDto);


    UpdateEventRequest fromDto(UpdateEventRequestDto requestDto);

    @Mapping(
            target = "venueId",
            source = "venue.id"
    )
    EventDto toDto(Event event);

}

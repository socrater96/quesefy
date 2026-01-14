package com.boveda.quesefy.mapper;

import com.boveda.quesefy.domain.CreateEventRequest;
import com.boveda.quesefy.domain.UpdateEventRequest;
import com.boveda.quesefy.domain.dto.CreateEventRequestDto;
import com.boveda.quesefy.domain.dto.EventDto;
import com.boveda.quesefy.domain.dto.UpdateEventRequestDto;
import com.boveda.quesefy.domain.entity.Event;

public interface EventMapper {

    CreateEventRequest fromDto(CreateEventRequestDto requestDto);

    UpdateEventRequest fromDto(UpdateEventRequestDto requestDto);

    EventDto toDto(Event event);

}

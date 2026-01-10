package com.boveda.quesfy.mapper;

import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.dto.CreateEventRequestDto;
import com.boveda.quesfy.domain.dto.EventDto;
import com.boveda.quesfy.domain.entity.Event;

public interface EventMapper {

    CreateEventRequest fromDto(CreateEventRequestDto requestDto);

    EventDto toDto(Event event);

}

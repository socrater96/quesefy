package com.boveda.quesfy.mapper.impl;

import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.UpdateEventRequest;
import com.boveda.quesfy.domain.dto.CreateEventRequestDto;
import com.boveda.quesfy.domain.dto.EventDto;
import com.boveda.quesfy.domain.dto.UpdateEventRequestDto;
import com.boveda.quesfy.domain.entity.Event;
import com.boveda.quesfy.mapper.EventMapper;
import org.springframework.stereotype.Component;

@Component
public class EventMapperImpl implements EventMapper {
    @Override
    public CreateEventRequest fromDto(CreateEventRequestDto requestDto) {
        return new CreateEventRequest(
                requestDto.title(),
                requestDto.description(),
                requestDto.date(),
                requestDto.type()
        );
    }

    @Override
    public UpdateEventRequest fromDto(UpdateEventRequestDto requestDto) {
        return new UpdateEventRequest(
                requestDto.title(),
                requestDto.description(),
                requestDto.date(),
                requestDto.type(),
                requestDto.status()
        );
    }

    @Override
    public EventDto toDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getType(),
                event.getStatus()
        );
    }
}

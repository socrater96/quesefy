package com.boveda.quesefy.mapper.impl;

import com.boveda.quesefy.domain.CreateEventRequest;
import com.boveda.quesefy.domain.UpdateEventRequest;
import com.boveda.quesefy.domain.dto.CreateEventRequestDto;
import com.boveda.quesefy.domain.dto.EventDto;
import com.boveda.quesefy.domain.dto.UpdateEventRequestDto;
import com.boveda.quesefy.domain.entity.Event;
import com.boveda.quesefy.mapper.EventMapper;
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

package com.boveda.quesfy.controller;

import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.dto.CreateEventRequestDto;
import com.boveda.quesfy.domain.dto.EventDto;
import com.boveda.quesfy.domain.entity.Event;
import com.boveda.quesfy.mapper.EventMapper;
import com.boveda.quesfy.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/events")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody CreateEventRequestDto createEventRequestDto) {
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        Event event = eventService.createEvent(createEventRequest);
        EventDto creetedEventDto = eventMapper.toDto(event);
        return new ResponseEntity<>(creetedEventDto, HttpStatus.CREATED);
    }
}

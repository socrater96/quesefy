package com.boveda.quesfy.controller;

import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.UpdateEventRequest;
import com.boveda.quesfy.domain.dto.CreateEventRequestDto;
import com.boveda.quesfy.domain.dto.EventDto;
import com.boveda.quesfy.domain.dto.UpdateEventRequestDto;
import com.boveda.quesfy.domain.entity.Event;
import com.boveda.quesfy.mapper.EventMapper;
import com.boveda.quesfy.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        EventDto createdEventDto = eventMapper.toDto(event);
        return new ResponseEntity<>(createdEventDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> listEvents() {

        List<Event> events = eventService.listEvents();
        List<EventDto> eventDtoList = events.stream()
                .map(eventMapper::toDto)
                .toList();

        return ResponseEntity.ok(eventDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable UUID id) {

        Event event = eventService.getEventById(id);
        EventDto eventDto = eventMapper.toDto(event);

        return ResponseEntity.ok(eventDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto
    ) {
        UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDto);
        Event event = eventService.updateEvent(id, updateEventRequest);
        EventDto updatedEventDto = eventMapper.toDto(event);

        return ResponseEntity.ok(updatedEventDto);
    }

}

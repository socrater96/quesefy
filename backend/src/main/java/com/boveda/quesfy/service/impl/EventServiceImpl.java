package com.boveda.quesfy.service.impl;

import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.entity.Event;
import com.boveda.quesfy.domain.entity.EventStatus;
import com.boveda.quesfy.repository.EventRepository;
import com.boveda.quesfy.service.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    @Override
    public Event createEvent(CreateEventRequest request) {
        Event event = new Event(
                null,
                request.title(),
                request.description(),
                request.date(),
                request.type(),
                EventStatus.DUE
        );
        return eventRepository.save(event);
    }
}

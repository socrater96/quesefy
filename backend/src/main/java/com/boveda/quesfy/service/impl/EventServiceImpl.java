package com.boveda.quesfy.service.impl;

import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.entity.Event;
import com.boveda.quesfy.domain.entity.EventStatus;
import com.boveda.quesfy.domain.exception.EventNotFoundException;
import com.boveda.quesfy.repository.EventRepository;
import com.boveda.quesfy.service.EventService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public List<Event> listEvents() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Override
    public Event getEventById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }
}

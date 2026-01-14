package com.boveda.quesfy.service.impl;

import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.UpdateEventRequest;
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

    @Override
    public Event updateEvent(UUID eventId, UpdateEventRequest request) {
        Event event = eventRepository.findById(eventId)
                        .orElseThrow(() -> new EventNotFoundException(eventId));

        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setDate(request.date());
        event.setType(request.type());
        event.setStatus(request.status());

        return eventRepository.save(event);
    }

}

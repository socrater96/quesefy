package com.boveda.quesefy.service.impl;

import com.boveda.quesefy.domain.CreateEventRequest;
import com.boveda.quesefy.domain.UpdateEventRequest;
import com.boveda.quesefy.domain.entity.Event;
import com.boveda.quesefy.domain.entity.Venue;
import com.boveda.quesefy.domain.exception.EventNotFoundException;
import com.boveda.quesefy.domain.exception.VenueNotFoundException;
import com.boveda.quesefy.repository.EventRepository;
import com.boveda.quesefy.repository.VenueRepository;
import com.boveda.quesefy.service.EventService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventServiceImpl(
            EventRepository eventRepository,
            VenueRepository venueRepository
    ) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public Event createEvent(CreateEventRequest request) {
        Event event = Event.builder()
                .title(request.title())
                .description(request.description())
                .date(request.date())
                .type(request.type())
                .build();

        if (request.venueId() != null) {
            Venue venue = venueRepository.findById(request.venueId())
                    .orElseThrow(() -> new VenueNotFoundException(request.venueId()));
            event.assignVenue(venue);
        }

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

        if (request.title() != null) {
            event.setTitle(request.title());
        }
        if (request.description() != null) {
            event.setDescription(request.description());
        }
        if (request.date() != null) {
            event.setDate(request.date());
        }
        if (request.type() != null) {
            event.setType(request.type());
        }
        if (request.status() != null) {
            event.setStatus(request.status());
        }
        if (request.venueId() != null) {
            Venue venue = venueRepository.findById(request.venueId())
                    .orElseThrow(() -> new VenueNotFoundException(request.venueId()));
            event.assignVenue(venue);
        }

        return eventRepository.save(event);
    }

}

package com.boveda.quesefy.service;

import com.boveda.quesefy.domain.CreateEventRequest;
import com.boveda.quesefy.domain.UpdateEventRequest;
import com.boveda.quesefy.domain.entity.Event;

import java.util.List;
import java.util.UUID;

public interface EventService {

    Event createEvent(CreateEventRequest request);

    List<Event> listEvents();

    Event getEventById(UUID id);

    Event updateEvent(UUID id, UpdateEventRequest request);

}

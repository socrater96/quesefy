package com.boveda.quesfy.service;

import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.UpdateEventRequest;
import com.boveda.quesfy.domain.entity.Event;

import java.util.List;
import java.util.UUID;

public interface EventService {

    Event createEvent(CreateEventRequest request);

    List<Event> listEvents();

    Event getEventById(UUID id);

    Event updateEvent(UUID id, UpdateEventRequest request);

}

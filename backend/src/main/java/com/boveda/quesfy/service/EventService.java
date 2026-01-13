package com.boveda.quesfy.service;

import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.entity.Event;

import java.util.List;

public interface EventService {

    Event createEvent(CreateEventRequest request);

    List<Event> listEvents();

}

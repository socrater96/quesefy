package com.boveda.quesfy.service;

import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.entity.Event;

public interface EventService {

    Event createEvent(CreateEventRequest request);

}

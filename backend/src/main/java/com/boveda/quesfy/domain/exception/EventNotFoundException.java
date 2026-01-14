package com.boveda.quesfy.domain.exception;

import java.util.UUID;

public class EventNotFoundException extends RuntimeException{
    private final UUID id;

    public UUID getId() {
        return id;
    }

    public EventNotFoundException(UUID id) {
        super("Event with id " + id + " not found");
        this.id = id;


    }
}

package com.boveda.quesefy.domain.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class VenueNotFoundException extends RuntimeException {
    private final UUID id;

    public VenueNotFoundException(UUID id) {
        super("Event with id " + id + " not found");
        this.id = id;

    }
}

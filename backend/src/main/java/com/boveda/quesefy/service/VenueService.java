package com.boveda.quesefy.service;

import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.UpdateVenueRequest;
import com.boveda.quesefy.domain.entity.Venue;

import java.util.List;
import java.util.UUID;

public interface VenueService {

    Venue createVenue(CreateVenueRequest request);

    Venue getById(UUID id);

    List<Venue> getAll();

    Venue update(UUID id, UpdateVenueRequest request);

    void delete(UUID id);

}

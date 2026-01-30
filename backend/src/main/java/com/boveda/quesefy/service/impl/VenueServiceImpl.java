package com.boveda.quesefy.service.impl;

import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.UpdateVenueRequest;
import com.boveda.quesefy.domain.entity.Venue;
import com.boveda.quesefy.mapper.VenueMapper;
import com.boveda.quesefy.repository.VenueRepository;
import com.boveda.quesefy.service.VenueService;

import java.util.List;
import java.util.UUID;

public class VenueServiceImpl implements VenueService {

    private VenueRepository venueRepository;

    private VenueMapper venueMapper;

    public VenueServiceImpl(VenueRepository venueRepository) {this.venueRepository = venueRepository;}

    @Override
    public Venue createVenue(CreateVenueRequest request) {
        Venue venue = Venue.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .venueType(request.venueType())
                .location(request.location())
                .build();

        return venueRepository.save(venue);
    }

    @Override
    public Venue getById(UUID id) {
        return null;
    }

    @Override
    public List<Venue> getAll() {
        return List.of();
    }

    @Override
    public Venue update(UUID id, UpdateVenueRequest request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}

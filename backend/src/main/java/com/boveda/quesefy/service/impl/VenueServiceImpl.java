package com.boveda.quesefy.service.impl;

import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.UpdateVenueRequest;
import com.boveda.quesefy.domain.entity.Venue;
import com.boveda.quesefy.domain.exception.EventNotFoundException;
import com.boveda.quesefy.domain.exception.VenueNotFoundException;
import com.boveda.quesefy.repository.VenueRepository;
import com.boveda.quesefy.service.VenueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    public VenueServiceImpl(VenueRepository venueRepository) {this.venueRepository = venueRepository;}

    @Override
    public Venue createVenue(CreateVenueRequest request) {
        Venue venue = Venue.builder()
                .name(request.name())
                .venueType(request.venueType())
                .location(request.location())
                .build();

        return venueRepository.save(venue);
    }

    @Override
    public Venue getById(UUID id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @Override
    public List<Venue> listVenues() {
        return List.of();
    }

    @Override
    public Venue update(UUID id, UpdateVenueRequest request) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException(id));

        if (request.name() != null) {
            venue.setName(request.name());
        }
        if (request.venueType() != null) {
            venue.setVenueType(request.venueType());
        }
        if (request.location() != null) {
            venue.updateLocation(request.location());
        }

        return venueRepository.save(venue);
    }

    @Override
    public void delete(UUID id) {

    }
}

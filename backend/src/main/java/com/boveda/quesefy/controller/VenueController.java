package com.boveda.quesefy.controller;

import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.dto.CreateVenueRequestDto;
import com.boveda.quesefy.domain.dto.VenueDto;
import com.boveda.quesefy.domain.entity.Venue;
import com.boveda.quesefy.mapper.VenueMapper;
import com.boveda.quesefy.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/venues")
public class VenueController {
    private final VenueService venueService;
    private final VenueMapper venueMapper;

    public VenueController(VenueService venueService, VenueMapper venueMapper){
        this.venueService = venueService;
        this.venueMapper = venueMapper;
    }

    @PostMapping
    public ResponseEntity<VenueDto> createVenue(@Valid @RequestBody CreateVenueRequestDto createVenueRequestDto){
        CreateVenueRequest createVenueRequest = venueMapper.fromDto(createVenueRequestDto);
        Venue createdVenue = venueService.createVenue(createVenueRequest);
        VenueDto createdVenueDto = venueMapper.toDto(createdVenue);

        return new ResponseEntity<>(createdVenueDto, HttpStatus.CREATED);
    }
}

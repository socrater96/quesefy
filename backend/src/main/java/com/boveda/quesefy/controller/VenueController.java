package com.boveda.quesefy.controller;

import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.UpdateVenueRequest;
import com.boveda.quesefy.domain.dto.*;
import com.boveda.quesefy.domain.entity.Venue;
import com.boveda.quesefy.mapper.VenueMapper;
import com.boveda.quesefy.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<VenueDto>> listVenues(){
        List<Venue> venueList = venueService.listVenues();

        List<VenueDto> venueDtoList = venueList.stream()
                .map(venueMapper::toDto)
                .toList();

        return new ResponseEntity<>(venueDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDto> getVenueById(@PathVariable UUID id) {
        Venue venue = venueService.getById(id);
        VenueDto venueDto = venueMapper.toDto(venue);

        return ResponseEntity.ok(venueDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueDto> updateVenue(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVenueRequestDto updateVenueRequestDto
    ) {
        UpdateVenueRequest updateVenueRequest = venueMapper.fromDto(updateVenueRequestDto);
        Venue venue = venueService.update(id, updateVenueRequest);
        VenueDto updatedVenueDto = venueMapper.toDto(venue);

        return ResponseEntity.ok(updatedVenueDto);
    }

}

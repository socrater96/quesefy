package com.boveda.quesefy.controller;

import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.UpdateVenueRequest;
import com.boveda.quesefy.domain.dto.*;
import com.boveda.quesefy.domain.entity.Venue;
import com.boveda.quesefy.mapper.VenueMapper;
import com.boveda.quesefy.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/venues")
@SecurityRequirement(name = "basicAuth")
public class VenueController {
    private final VenueService venueService;
    private final VenueMapper venueMapper;

    public VenueController(VenueService venueService, VenueMapper venueMapper){
        this.venueService = venueService;
        this.venueMapper = venueMapper;
    }

    @Operation(
            summary = "Create a new venue",
            description = "Creates a new venue and returns the created venue"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Venue created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VenueDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data"
            )
    })
    @PostMapping
    public ResponseEntity<VenueDto> createVenue(@Valid @RequestBody CreateVenueRequestDto createVenueRequestDto){
        CreateVenueRequest createVenueRequest = venueMapper.fromDto(createVenueRequestDto);
        Venue createdVenue = venueService.createVenue(createVenueRequest);
        VenueDto createdVenueDto = venueMapper.toDto(createdVenue);

        return new ResponseEntity<>(createdVenueDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "List venues",
            description = "Lists all venues that have been created"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of venues returned successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = VenueDto.class)
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<VenueDto>> listVenues(){
        List<Venue> venueList = venueService.listVenues();

        List<VenueDto> venueDtoList = venueList.stream()
                .map(venueMapper::toDto)
                .toList();

        return new ResponseEntity<>(venueDtoList, HttpStatus.OK);
    }

    @Operation(
            summary = "Get venue by ID",
            description = "Returns the venue with the given id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Returns found venue",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VenueDto.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<VenueDto> getVenueById(@PathVariable UUID id) {
        Venue venue = venueService.getById(id);
        VenueDto venueDto = venueMapper.toDto(venue);

        return ResponseEntity.ok(venueDto);
    }

    @Operation(
            summary = "Update venue",
            description = "Updates an existing venue"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Venue updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VenueDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
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

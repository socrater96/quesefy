package com.boveda.quesefy.mapper;


import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.dto.CreateVenueRequestDto;
import com.boveda.quesefy.domain.dto.VenueDto;
import com.boveda.quesefy.domain.entity.Venue;

public interface VenueMapper {

    CreateVenueRequest fromDto(CreateVenueRequestDto createVenueRequestDto);

    VenueDto toDto(Venue venue);

}

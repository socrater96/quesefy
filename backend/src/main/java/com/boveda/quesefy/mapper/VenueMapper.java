package com.boveda.quesefy.mapper;


import com.boveda.quesefy.domain.dto.VenueDto;
import com.boveda.quesefy.domain.entity.Venue;

public interface VenueMapper {
    VenueDto toDto(Venue venue);

}

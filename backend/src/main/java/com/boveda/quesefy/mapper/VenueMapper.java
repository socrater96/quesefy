package com.boveda.quesefy.mapper;


import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.UpdateVenueRequest;
import com.boveda.quesefy.domain.dto.CreateVenueRequestDto;
import com.boveda.quesefy.domain.dto.LocationDto;
import com.boveda.quesefy.domain.dto.UpdateVenueRequestDto;
import com.boveda.quesefy.domain.dto.VenueDto;
import com.boveda.quesefy.domain.entity.Location;
import com.boveda.quesefy.domain.entity.Venue;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface VenueMapper {

    CreateVenueRequest fromDto(CreateVenueRequestDto dto);

    UpdateVenueRequest fromDto(UpdateVenueRequestDto dto);

    VenueDto toDto(Venue venue);

    Location fromDto(LocationDto dto);

    LocationDto toDto(Location location);

}

package com.boveda.quesefy.mapper.impl;

import com.boveda.quesefy.domain.dto.LocationDto;
import com.boveda.quesefy.domain.dto.VenueDto;
import com.boveda.quesefy.domain.entity.Location;
import com.boveda.quesefy.domain.entity.Venue;
import com.boveda.quesefy.mapper.VenueMapper;
import org.springframework.stereotype.Component;

@Component
public class VenueMapperImpl implements VenueMapper {
    @Override
    public VenueDto toDto(Venue venue) {
        return new VenueDto(
                venue.getId(),
                venue.getName(),
                venue.getVenueType(),
                toLocationDto(venue.getLocation())
        );
    }

    private LocationDto toLocationDto(Location location) {
        if(location == null) return null;

        return new LocationDto(
                location.getAddress(),
                location.getCity(),
                location.getProvince(),
                location.getZipcode(),
                location.getCountry(),
                location.getLatitude(),
                location.getLongitude()
        );
    }

}

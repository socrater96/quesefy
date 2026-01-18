package com.boveda.quesefy.utils;

import com.boveda.quesefy.domain.dto.LocationDto;
import com.boveda.quesefy.domain.entity.*;



public class TestDataFactory {


    public static Location createLocation() {
        return new Location("Orzán, 19", "A Coruña", "A Coruña", "15001", "Spain", 43.371909, -8.400471);
    }

    public static LocationDto createLocationDto() {
        return new LocationDto("Orzán, 19", "A Coruña", "A Coruña", "15001", "Spain", 43.371909, -8.400471);
    }

}

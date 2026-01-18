package com.boveda.quesefy.domain;

import com.boveda.quesefy.domain.entity.Location;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LocationTest {

    @Test
    void shouldCreateLocation() {
        Location location = new Location(
                "Orzán, 18",
                "A Coruña",
                "A Coruña",
                "15003",
                "Spain",
                40.4168,
                -3.7038
        );

        assertThat(location.getAddress()).isEqualTo("Orzán, 18");
        assertThat(location.getCity()).isEqualTo("A Coruña");
        assertThat(location.getZipcode()).isEqualTo("15003");
        assertThat(location.getCountry()).isEqualTo("Spain");
        assertThat(location.getLatitude()).isEqualTo(40.4168);
        assertThat(location.getLongitude()).isEqualTo(-3.7038);
    }


}


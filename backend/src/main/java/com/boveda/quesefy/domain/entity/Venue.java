package com.boveda.quesefy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "venues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venue {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VenueType venueType;

    @Embedded
    private Location location;

    public void updateLocation(Location newLocation) {
        if (newLocation.getAddress() != null) {
            this.location.setAddress(newLocation.getAddress());
        }
        if (newLocation.getCity() != null) {
            this.location.setCity(newLocation.getCity());
        }
        if (newLocation.getProvince() != null) {
            this.location.setProvince(newLocation.getProvince());
        }
        if (newLocation.getZipcode() != null) {
            this.location.setZipcode(newLocation.getZipcode());
        }
        if (newLocation.getCountry() != null) {
            this.location.setCountry(newLocation.getCountry());
        }

        if (newLocation.getLatitude() != null) {
            this.location.setLatitude(newLocation.getLatitude());
        }
        if (newLocation.getLongitude() != null) {
            this.location.setLongitude(newLocation.getLongitude());
        }
    }

}

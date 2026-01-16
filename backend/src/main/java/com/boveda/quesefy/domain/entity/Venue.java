package com.boveda.quesefy.domain.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "venues")
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

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VenueType getVenueType() {
        return venueType;
    }

    public Location getLocation() {
        return location;
    }


}

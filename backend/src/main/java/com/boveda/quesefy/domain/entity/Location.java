package com.boveda.quesefy.domain.entity;

import jakarta.persistence.Embeddable;
/**

 * It is currently embedded because it only belongs to a single Venue
 * and is not reused elsewhere.

 * May be promoted to a separate entity if necessary.
 */
@Embeddable
public class Location {

    private String address;
    private String city;
    private String province;
    private String zipcode;
    private String country;

    private Double latitude;
    private Double longitude;

}
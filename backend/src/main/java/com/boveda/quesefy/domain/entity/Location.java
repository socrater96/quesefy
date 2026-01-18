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

    public Location(String address, String city, String province, String zipcode, String country, Double latitude, Double longitude) {
        this.address = address;
        this.city = city;
        this.province = province;
        this.zipcode = zipcode;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCountry() {
        return country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
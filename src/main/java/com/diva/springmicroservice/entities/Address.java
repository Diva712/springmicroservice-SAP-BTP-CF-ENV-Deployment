package com.diva.springmicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {

    public Address() {
    }

    public Address(String addressId, String addressType, String street, String city, String country, String region) {
        this.addressId = addressId;
        this.addressType = addressType;
        this.street = street;
        this.city = city;
        this.country = country;
        this.region = region;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, name = "ID")
    private String addressId;

    @Column(nullable = false, name = "ADDRESS_TYPE")
    private String addressType;

    @Column(nullable = false, name = "STREET")
    private String street;

    @Column(nullable = false, name = "CITY")
    private String city;

    @Column(nullable = false, name = "COUNTRY")
    private String country;

    @Column(nullable = false, name = "REGION")
    private String region;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}

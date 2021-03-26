package org.justinbaur.bankteller.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Address POJO. Each customer has address information defining their location.
 */
public class Address {
    @NotBlank(message = "street is missing")
    private String street;
    @NotBlank(message = "state is missing")
    @Size(min = 2, max = 2, message = "state must be 2 letters")
    private String state;
    @NotBlank(message = "city is missing")
    private String city;
    @NotBlank(message = "country is missing")
    private String country;
    @NotBlank(message = "zip code is missing or invalid")
    private Integer zipCode;

    public Address() {

    }

    public Address(String street, String state, String city, String country, Integer zipCode) {
        this.street = street;
        this.state = state;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address [city=" + city + ", country=" + country + ", state=" + state + ", street=" + street
                + ", zipCode=" + zipCode + "]";
    }

}

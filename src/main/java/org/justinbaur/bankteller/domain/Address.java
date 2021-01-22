package org.justinbaur.bankteller.domain;

public class Address {
    private String street;
    private String state;
    private String city;
    private String country;
    private String zipCode;

    public Address(){
        
    }

    public Address(String street, String state, String city, String country, String zipCode) {
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address [city=" + city + ", country=" + country + ", state=" + state + ", street=" + street
                + ", zipCode=" + zipCode + "]";
    }

    
}

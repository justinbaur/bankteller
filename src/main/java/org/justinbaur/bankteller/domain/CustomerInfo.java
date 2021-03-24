package org.justinbaur.bankteller.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CustomerInfo POJO. Names and addresses.
 */
public class CustomerInfo {

    @NotBlank(message = "first name is missing")
    private String firstName;
    @NotBlank(message = "last name is missing")
    private String lastName;
    @NotNull(message = "account is missing")
    private Address address;

    public CustomerInfo() {
    }

    public CustomerInfo(String firstName, String lastName, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer [address=" + address + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}

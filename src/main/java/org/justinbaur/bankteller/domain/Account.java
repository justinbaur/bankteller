package org.justinbaur.bankteller.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Account {

    @Id
    private Integer id;
    private Integer balance;
    private Date accountCreated;
    private String accountType;

    private String firstName;
    private String lastName;
    private Address address;

    public Account() {
    }

    public Account(Integer id, Integer balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account(Integer id, Integer balance, Date accountCreated, String accountType, String firstName,
            String lastName, Address address) {
        this.id = id;
        this.balance = balance;
        this.accountCreated = accountCreated;
        this.accountType = accountType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Date getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Date accountCreated) {
        this.accountCreated = accountCreated;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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
        return "Account [accountCreated=" + accountCreated + ", accountType=" + accountType + ", address=" + address
                + ", balance=" + balance + ", firstName=" + firstName + ", id=" + id + ", lastName=" + lastName + "]";
    }

}

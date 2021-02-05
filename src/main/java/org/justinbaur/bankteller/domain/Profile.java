package org.justinbaur.bankteller.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Profile {

    @Id
    @JsonProperty("_id")
    private String id;
    @BsonProperty(value = "isAdmin")
    private Boolean isAdmin;
    @BsonProperty(value = "accountCreated")
    private Date accountCreated;
    private CustomerInfo customer;
    private List<Account> accounts;

    public Profile() {
    }

    public Profile(String id, Boolean isAdmin, Date accountCreated, CustomerInfo customer, List<Account> accounts) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.accountCreated = accountCreated;
        this.customer = customer;
        this.accounts = accounts;
    }



    @Override
    public String toString() {
        return "Profile [accountCreated=" + accountCreated + ", accounts=" + accounts + ", customer=" + customer
                + ", id=" + id + ", isAdmin=" + isAdmin + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Date getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Date accountCreated) {
        this.accountCreated = accountCreated;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfo customer) {
        this.customer = customer;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

}

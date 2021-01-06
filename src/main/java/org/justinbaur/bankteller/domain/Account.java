package org.justinbaur.bankteller.domain;

public class Account {

    private Integer id;
    private Integer balance;

    public Account() {
    }

    public Account(Integer id, Integer balance) {
        this.id = id;
        this.balance = balance;
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

    public String toString() {
        return "Account [ id: " + id + ", balance: " + balance + " ]";
    }
}

package org.justinbaur.bankteller.service;

public class AccountServiceInMemoryImpl implements AccountService {
    private Integer balance = 0;

    private final Integer accountID;
    private String username;

    public AccountServiceInMemoryImpl(Integer accountID, String username) {
        this.accountID = accountID;
        this.username = username;
    }

    public Integer getID() {
        return this.accountID;
    }

    public String getName() {
        return this.username;
    }

    public Integer getBalance() {
        return balance;
    }

    public void addBalance(Integer addAmount) {
        balance = getBalance() + addAmount;
    }

    public void subtractBalance(Integer subtractAmount) {
        balance = getBalance() - subtractAmount;
    }
}

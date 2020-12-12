package org.justinbaur.bankteller.service;

import org.springframework.stereotype.Component;

@Component
public class AccountService {
    private Integer balance = 0;

    public Integer getBalance(){
        return balance;
    }

    public void setBalance(Integer newBalance){
        balance = newBalance;
    }

    public void addBalance(Integer addAmount){
        setBalance(getBalance() + addAmount);
    }
}

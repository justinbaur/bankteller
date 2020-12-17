package org.justinbaur.bankteller.service;

import org.springframework.stereotype.Component;

@Component
public class AccountServiceInMemoryImpl implements AccountService{
    private Integer balance = 0;

    public Integer getBalance(){
        return balance;
    }

    public void addBalance(Integer addAmount){
        balance = getBalance() + addAmount;
    }

    public void subtractBalance(Integer subtractAmount){
        balance = getBalance() - subtractAmount;
    }
}

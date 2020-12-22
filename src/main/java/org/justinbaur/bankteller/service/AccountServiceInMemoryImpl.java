package org.justinbaur.bankteller.service;

import java.util.HashMap;
import java.util.Map;

import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceInMemoryImpl implements AccountService {

    Map<Integer, Integer> accounts = new HashMap<>();

    Time myTime = Time.getInstance();

    public AccountServiceInMemoryImpl(){
        System.out.println(myTime.printTime() + " - Setting up in memory bank accounts");
        accounts.put(Integer.valueOf(1010), 0);
        accounts.put(Integer.valueOf(2020), 20000000);
    }

    public Integer getBalance(Integer accountId) throws AccountNotFound {
        if (accounts.containsKey(accountId)) {
            return accounts.get(accountId);
        }
        else {
            throw new AccountNotFound("No account found");
        }
    }

    public void addBalance(Integer accountId, Integer addAmount) throws AccountNotFound {
        if (accounts.containsKey(accountId)) {
            accounts.put(accountId, getBalance(accountId) + addAmount);
        }
        else{
            throw new AccountNotFound("No account found");
        }
    }

    public void subtractBalance(Integer accountId, Integer subtractAmount) throws AccountNotFound {
        if (accounts.containsKey(accountId)) {
            accounts.put(accountId, getBalance(accountId) - subtractAmount);
        }
        else{
            throw new AccountNotFound("No account found");
        }
    }

    @Override
    public Boolean checkAccount(Integer accountId) {
        return accounts.containsKey(accountId);
    }
}

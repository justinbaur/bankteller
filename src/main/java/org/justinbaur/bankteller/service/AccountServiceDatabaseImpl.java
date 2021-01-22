package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.exceptions.UpdateException;
import org.justinbaur.bankteller.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceDatabaseImpl implements AccountService {

    @Autowired
    AccountRepository repository;

    @Override
    public Integer getBalance(Integer accountId) throws AccountNotFound {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addBalance(Integer accountId, Integer addAmount) throws AccountNotFound, UpdateException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void subtractBalance(Integer accountId, Integer subtractAmount) throws AccountNotFound, UpdateException {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean checkAccount(Integer accountId) {
        // TODO Auto-generated method stub
        return null;
    }
    
}

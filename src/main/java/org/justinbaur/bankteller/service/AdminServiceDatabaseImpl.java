package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminServiceDatabaseImpl implements AdminService {

    @Autowired
    AccountRepository repository;
    
    @Override
    public void createAccount(Integer balance) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAccount(Integer accountId) throws AccountNotFound {
        // TODO Auto-generated method stub

    }

    @Override
    public void printReportByState(String state){

    }
    
}

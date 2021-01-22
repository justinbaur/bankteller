package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.exceptions.AccountNotFound;

public interface AdminService {

    public void createAccount(Integer balance);
    public void deleteAccount(Integer accountId) throws AccountNotFound;
    public void printReportByState(String state);

}
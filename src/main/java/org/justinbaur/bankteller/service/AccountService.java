package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.exceptions.AccountNotFound;

public interface AccountService {

    //admin-only commands
    public void createAccount(Integer balance);
    public void deleteAccount(Integer accountId)  throws AccountNotFound;

    //user commands
    public Integer getBalance(Integer accountId) throws AccountNotFound;
    public void addBalance(Integer accountId, Integer addAmount) throws AccountNotFound;
    public void subtractBalance(Integer accountId, Integer subtractAmount) throws AccountNotFound;
	public Boolean checkAccount(Integer accountId);
    
}

package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.exceptions.UpdateException;

public interface AccountService {

    public Integer getBalance(Integer accountId) throws AccountNotFound;
    public void addBalance(Integer accountId, Integer addAmount) throws AccountNotFound, UpdateException;
    public void subtractBalance(Integer accountId, Integer subtractAmount) throws AccountNotFound, UpdateException;
	public Boolean checkAccount(Integer accountId);
    
}

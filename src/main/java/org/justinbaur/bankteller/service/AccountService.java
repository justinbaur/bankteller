package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.Exceptions.AccountNotFound;

public interface AccountService {

    public Integer getBalance(Integer accountId) throws AccountNotFound;
    public void addBalance(Integer accountId, Integer addAmount) throws AccountNotFound;
    public void subtractBalance(Integer accountId, Integer subtractAmount) throws AccountNotFound;
	public Boolean checkAccount(Integer accountId);
    
}

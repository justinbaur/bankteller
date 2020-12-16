package org.justinbaur.bankteller.service;

public interface AccountService {

    public Integer getBalance();
    public void addBalance(Integer addAmount);
    public void subtractBalance(Integer subtractAmount);
    
}

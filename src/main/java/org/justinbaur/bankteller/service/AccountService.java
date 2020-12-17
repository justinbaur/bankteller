package org.justinbaur.bankteller.service;

public interface AccountService {

    public Integer getID();
    public String getName();
    public Integer getBalance();
    public void addBalance(Integer addAmount);
    public void subtractBalance(Integer subtractAmount);
    
}

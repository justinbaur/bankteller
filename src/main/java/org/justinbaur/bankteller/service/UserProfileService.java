package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.exceptions.ProfileNotFound;
import org.justinbaur.bankteller.exceptions.UpdateException;

public interface UserProfileService extends ProfileService{

    public Integer getBalance(String profileId, String accountName) throws ProfileNotFound, AccountNotFound;
    public void addBalance(String profileId, String accountName, Integer addAmount) throws ProfileNotFound, AccountNotFound, UpdateException;
    public void subtractBalance(String profileId, String accountName, Integer subtractAmount) throws ProfileNotFound, AccountNotFound, UpdateException;
    
}

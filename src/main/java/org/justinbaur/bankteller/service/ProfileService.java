package org.justinbaur.bankteller.service;

import java.util.Map;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exceptions.ProfileNotFound;

public interface ProfileService {

    public Boolean checkProfile(String profileId) throws ProfileNotFound;
    public Profile getProfile(String profileId) throws ProfileNotFound;
    public Map<String, Account> getAccountsMap(String profileId) throws ProfileNotFound;
    
}
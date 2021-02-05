package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.exceptions.ProfileNotFound;
import org.justinbaur.bankteller.exceptions.UpdateException;
import org.justinbaur.bankteller.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProfileServiceDatabaseImpl extends ProfileServiceImpl implements UserProfileService {

    @Autowired
    ProfileRepository repository;

    @Override
    public Integer getBalance(String profileId, String accountName) throws ProfileNotFound, AccountNotFound {
        Profile profile = getProfile(profileId);
        if (profile != null) {
            if (getAccountsMap(profileId).containsKey(accountName)) {
                return getAccountsMap(profileId).get(accountName).getBalance();
            } else {
                throw new AccountNotFound("No account found.");
            }
        } else {
            throw new ProfileNotFound("No profile found.");
        }
    }

    @Override
    public void addBalance(String profileId, String accountName, Integer addAmount)
            throws ProfileNotFound, AccountNotFound, UpdateException {
        Profile profile = getProfile(profileId);
        if (profile != null) {
            if (getAccountsMap(profileId).containsKey(accountName)) {
                getAccountsMap(profileId).get(accountName).setBalance(getBalance(profileId, accountName) + addAmount);
                repository.save(profile);
            } else {
                throw new AccountNotFound("No account found.");
            }
        } else {
            throw new ProfileNotFound("No profile found.");
        }
    }

    @Override
    public void subtractBalance(String profileId, String accountName, Integer subtractAmount)
            throws ProfileNotFound, AccountNotFound, UpdateException {
        Profile profile = getProfile(profileId);
        if (profile != null) {
            if (getAccountsMap(profileId).containsKey(accountName)) {
                getAccountsMap(profileId).get(accountName)
                        .setBalance(getBalance(profileId, accountName) - subtractAmount);
                repository.save(profile);
            } else {
                throw new AccountNotFound("No account found.");
            }
        } else {
            throw new ProfileNotFound("No profile found.");
        }
    }
}

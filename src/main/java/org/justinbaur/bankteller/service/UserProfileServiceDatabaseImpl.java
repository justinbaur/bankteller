package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.exceptions.InsufficientBalance;
import org.justinbaur.bankteller.exceptions.ProfileNotFound;
import org.justinbaur.bankteller.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProfileServiceDatabaseImpl extends ProfileServiceImpl implements UserProfileService {

    ProfileRepository repository;

    public UserProfileServiceDatabaseImpl(@Autowired ProfileRepository repository) {
        super(repository);
    }

    @Override
    public Integer getBalance(String profileId, String accountName) throws ProfileNotFound, AccountNotFound {
        Profile profile = getProfile(profileId);
        Account account = null;

        account = profile.getAccount(accountName);
        if (account != null) {
            return account.getBalance();
        } else {
            throw new AccountNotFound("No account found.");
        }
    }

    @Override
    public void addBalance(String profileId, String accountName, Integer addAmount)
            throws ProfileNotFound, AccountNotFound {
        Profile profile = getProfile(profileId);
        Account account = null;

        account = profile.getAccount(accountName);
        if (account != null) {
            profile.getAccount(accountName).setBalance(getBalance(profileId, accountName) + addAmount);
            repository.save(profile);
        } else {
            throw new AccountNotFound("No account found.");
        }
    }

    @Override
    public void subtractBalance(String profileId, String accountName, Integer subtractAmount)
            throws ProfileNotFound, AccountNotFound, InsufficientBalance {
        Profile profile = getProfile(profileId);
        Account account = null;

        if (profile != null) {
            account = profile.getAccount(accountName);
            if (account != null) {
                if (subtractAmount <= account.getBalance()) {
                    profile.getAccount(accountName).setBalance(getBalance(profileId, accountName) - subtractAmount);
                    repository.save(profile);
                } else {
                    throw new InsufficientBalance("Insufficient balance.");
                }
            } else {
                throw new AccountNotFound("No account found.");
            }
        } else {
            throw new ProfileNotFound("No profile found.");
        }

    }
}

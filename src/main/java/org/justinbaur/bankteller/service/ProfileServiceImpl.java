package org.justinbaur.bankteller.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exceptions.ProfileNotFound;
import org.justinbaur.bankteller.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository repository;

    @Override
    public Boolean checkProfile(String profileId) throws ProfileNotFound {
        if (repository.existsById(profileId)) {
            return true;
        } else {
            System.out.println("Nothing here.. " + profileId);
            throw new ProfileNotFound("No profile found.");
        }
    }

    @Override
    public Profile getProfile(String profileId) throws ProfileNotFound {
        if (checkProfile(profileId)) {
            Optional<Profile> profile = repository.findById(profileId);
            return profile.get();
        } else {
            throw new ProfileNotFound("No profile found.");
        }
    }

    public Map<String, Account> getAccountsMap(String profileId) throws ProfileNotFound {
        Map<String, Account> accountsMap = new HashMap<String, Account>();
        List<Account> accounts = getProfile(profileId).getAccounts();
        for (Account account : accounts) {
            accountsMap.put(account.getAccountType(), account);
        }
        return accountsMap;
    }
}

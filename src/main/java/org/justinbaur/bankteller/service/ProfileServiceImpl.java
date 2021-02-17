package org.justinbaur.bankteller.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exceptions.NoExistingAccounts;
import org.justinbaur.bankteller.exceptions.ProfileNotFound;
import org.justinbaur.bankteller.repository.ProfileRepository;

public class ProfileServiceImpl implements ProfileService {

    ProfileRepository repository;

    public ProfileServiceImpl(ProfileRepository repository){
        this.repository = repository;
    }

    @Override
    public Boolean checkProfile(String profileId) throws ProfileNotFound {
        if (repository.existsById(profileId)) {
            return true;
        } else {
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

    public Map<String, Account> getAccountsMap(String profileId) throws ProfileNotFound, NoExistingAccounts {
        Map<String, Account> accountsMap = new HashMap<String, Account>();
        List<Account> accounts = getProfile(profileId).getAccounts();
        for (Account account : accounts) {
            accountsMap.put(account.getAccountName(), account);
        }
        if(accountsMap.isEmpty()){
            throw new NoExistingAccounts("This profile has no existing accounts.");
        }
        return accountsMap;
    }
}

package org.justinbaur.bankteller.service;

import java.util.List;
import java.util.Optional;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exception.AccountNotFound;
import org.justinbaur.bankteller.exception.ProfileNotFound;
import org.justinbaur.bankteller.repository.ProfileRepository;

public class ProfileServiceImpl implements ProfileService {

    ProfileRepository repository;

    public ProfileServiceImpl(ProfileRepository repository){
        this.repository = repository;
    }

    @Override
    public Boolean checkProfile(String profileId) throws ProfileNotFound {
        Boolean profileExists = false;
        if (repository.existsById(profileId)) {
            profileExists = true;
        } else {
            throw new ProfileNotFound("No profile found.");
        }
        return profileExists;
    }

    @Override
    public Profile getProfile(String profileId) throws ProfileNotFound {
        Profile profile = null;
        if (checkProfile(profileId)) {
            Optional<Profile> foundProfile = repository.findById(profileId);
            profile = foundProfile.get();
        }
        return profile;
    }

    @Override
    public List<Profile> getProfiles() {
        List<Profile> profileList = repository.findAll();
        return profileList;
    }

    @Override
    public Account getAccount(String profileId, String accountName) throws ProfileNotFound, AccountNotFound {
        Profile profile = getProfile(profileId);
        return profile.getAccount(accountName);
    }

    @Override
    public List<Account> getAccounts(String profileId) throws ProfileNotFound {
        Profile profile = getProfile(profileId);
        return profile.getAccounts();
    }
}

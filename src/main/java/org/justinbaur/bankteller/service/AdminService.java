package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.domain.Address;
import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.exceptions.ProfileNotFound;

public interface AdminService extends ProfileService {

    public void createProfile(String firstName, String lastName, Address address);
    public void deleteProfile(String profileId) throws ProfileNotFound;
    public void createAccount(String profileId, String accountName, String accountType, Integer balance) throws ProfileNotFound;
    public void deleteAccount(String profileId, String accountName) throws ProfileNotFound, AccountNotFound;
    public void printReportByState(String state);

}
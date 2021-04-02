package org.justinbaur.bankteller.service;

import java.util.List;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exception.AccountNotFound;
import org.justinbaur.bankteller.exception.ProfileNotFound;

/**
 * Service interface representing underlying methods for actions for both admin
 * and non-admin user accounts, to be extended.
 */
public interface ProfileService {

    /**
     * Checks if a Profile exists in the database by supplying a String ID.
     * 
     * @param profileId String ID matching the Profile to be checked
     * @return Boolean true if profile exists in database, false if profile does not
     *         exist in database
     */
    public Boolean checkProfile(String profileId) throws ProfileNotFound;

    /**
     * Get the Profile object by supplying a String ID.
     * 
     * @param profileId String ID matching the Profile to be obtained
     * @return Profile object from specified ID
     * @throws ProfileNotFound throw this exception when the String ID does not
     *                         exist in the database
     */
    public Profile getProfile(String profileId) throws ProfileNotFound;

    /**
     * Get a list of all Profile objects stored in the database.
     * 
     * @return List of Profile objects.
     */
    public List<Profile> getProfiles();

    /**
     * Get a specific Account object by name from a specific Profile matching
     * profileId.
     * 
     * @param profileId   String ID matching the Profile to obtain an Account object
     *                    from
     * @param accountName The name of the account to retrieve
     * @return Account object that matches name with accountName
     * @throws ProfileNotFound throw this exception when the String ID for profile
     *                         does not exist in the database
     * @throws AccountNotFound throw this exception when the String name for Account
     *                         does not exist in the database for the given Profile
     */
    public Account getAccount(String profileId, String accountName) throws ProfileNotFound, AccountNotFound;

    /**
     * Get a List of all Account objects for a given Profile matching profileId.
     * 
     * @param profileId String ID matching the Profile to obtain all Account objects
     *                  from
     * @return List of all Account objects stored within the Profile
     * @throws ProfileNotFound throw this exception when the String ID does not
     *                         exist in the database
     */
    public List<Account> getAccounts(String profileId) throws ProfileNotFound;

}
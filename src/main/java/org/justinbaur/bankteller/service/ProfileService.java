package org.justinbaur.bankteller.service;

import java.util.Map;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exceptions.NoExistingAccounts;
import org.justinbaur.bankteller.exceptions.ProfileNotFound;

/**
 * Service interface representing underlying methods for actions for both admin and non-admin user accounts, to be extended.
 */
public interface ProfileService {

    /**
     * Checks if a profile exists in the database by supplying a String ID.
     * 
     * @param profileId String ID matching the profile to be checked
     * @return Boolean true if profile exists in database, false if profile does not
     *         exist in database
     * @throws ProfileNotFound throw this exception when the String ID does not
     *                         exist in the database
     */
    public Boolean checkProfile(String profileId) throws ProfileNotFound;

    /**
     * Get the Profile object by supplying a String ID.
     * 
     * @param profileId String ID matching the profile to be obtained
     * @return Profile object from specified ID
     * @throws ProfileNotFound throw this exception when the String ID does not
     *                         exist in the database
     */
    public Profile getProfile(String profileId) throws ProfileNotFound;

    /**
     * Retrieve the map of account names and matching Accounts, useful for checking
     * if accounts exist and pulling information from an Account by specifying an
     * account name.
     * 
     * @param profileId String ID matching the profile to retrieve the account map
     *                  from
     * @return Map of String Account names and matching Account objects
     * @throws ProfileNotFound    throw this exception when the String ID does not
     *                            exist in the database
     * @throws NoExistingAccounts throw this exception when the Profile has no
     *                            existing accounts
     */
    public Map<String, Account> getAccountsMap(String profileId) throws ProfileNotFound, NoExistingAccounts;

}
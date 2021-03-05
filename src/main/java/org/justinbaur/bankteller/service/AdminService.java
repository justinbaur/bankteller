package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Address;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exception.AccountNotFound;
import org.justinbaur.bankteller.exception.ProfileNotFound;

/**
 * Service interface representing underlying methods for actions for admin-level accounts.
 */
public interface AdminService extends ProfileService {

    /**
     * Insert a new profile into the database with given personal information.
     * 
     * @param profile Profile object to be inserted into the database.
     */
    public void createProfile(Profile profile);

    /**
     * Insert a new profile into the database with given personal information.
     * 
     * @param firstName first name of the customer
     * @param lastName  last name of the customer
     * @param address   customer's address information
     */
    public void createProfile(String firstName, String lastName, Address address);

    /**
     * Update a Profile in the database.
     * 
     * @param profile Profile object to be updated in the database.
     */
    public void updateProfile(Profile profile);

    /**
     * Delete an existing profile from the database by specifying a String ID.
     * 
     * @param profileId String ID matching the profile to be deleted
     * @throws ProfileNotFound throw this exception when the String ID does not
     *                         exist in the database
     */
    public void deleteProfile(String profileId) throws ProfileNotFound;

    /**
     * Create a new account to hold money, for an existing profile in the database.
     * 
     * @param profileId   String ID matching the profile to create an account in
     * @param account Account object to add to profile
     * @throws ProfileNotFound throw this exception when the String ID does not
     *                         exist in the database
     */
    public void createAccount(String profileId, Account account) throws ProfileNotFound;

    /**
     * Create a new account to hold money, for an existing profile in the database.
     * 
     * @param profileId   String ID matching the profile to create an account in
     * @param accountName Desired name of the account
     * @param accountType Desired account type, different account types may be
     *                    subject to different rules
     * @param balance     Desired starting balance of the account
     * @throws ProfileNotFound throw this exception when the String ID does not
     *                         exist in the database
     */
    public void createAccount(String profileId, String accountName, String accountType, Integer balance)
            throws ProfileNotFound;

    /**
     * Delete an existing account for a profile in the database.
     * 
     * @param profileId   String ID matching the profile to delete an account in
     * @param accountName Desired name of the account to be deleted
     * @throws ProfileNotFound throw this exception when the String ID does not
     *                         exist in the database
     * @throws AccountNotFound throw this exception when the String accountName does
     *                         not exist for the profile in the database
     */
    public void deleteAccount(String profileId, String accountName) throws ProfileNotFound, AccountNotFound;

    /**
     * Example database query method using a US state input as a filter. Displays
     * all profiles with a matching state in their address.
     * 
     * @param state Should be a capitalized, 2-letter abbreviation String for a US
     *              state to filter the customers by
     */
    public void printReportByState(String state);

}
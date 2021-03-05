package org.justinbaur.bankteller.service;

import org.justinbaur.bankteller.exception.AccountNotFound;
import org.justinbaur.bankteller.exception.InsufficientBalance;
import org.justinbaur.bankteller.exception.ProfileNotFound;

/**
 * Service interface representing underlying methods for actions for non-admin user accounts.
 */
public interface UserProfileService extends ProfileService {

    /**
     * Get the balance for an Account.
     * 
     * @param profileId   String ID matching the profile to retrieve account
     *                    balances from
     * @param accountName String name matching the account to retrieve the balance
     *                    from
     * @return Integer amount representing the balance in the account
     * @throws ProfileNotFound throw this exception when the String ID does not
     *                         exist in the database
     * @throws AccountNotFound throw this exception when the String accountName does
     *                         not exist for the profile in the database
     */
    public Integer getBalance(String profileId, String accountName) throws ProfileNotFound, AccountNotFound;

    /**
     * Add to the balance for an Account.
     * 
     * @param profileId   String ID matching the profile to add account balances to
     * @param accountName String name matching the account to add to
     * @param addAmount   Integer amount to be added
     * @throws ProfileNotFound throw this exception when the String ID does not
     *                         exist in the database
     * @throws AccountNotFound throw this exception when the String accountName does
     *                         not exist for the profile in the database
     */
    public void addBalance(String profileId, String accountName, Integer addAmount)
            throws ProfileNotFound, AccountNotFound;

    /**
     * Subtract from the balance in an Account.
     * 
     * @param profileId      String ID matching the profile to subtract from the
     *                       balance of
     * @param accountName    String name matching the account to subtract from
     * @param subtractAmount Integer amount to be subtracted
     * @throws ProfileNotFound     throw this exception when the String ID does not
     *                             exist in the database
     * @throws AccountNotFound     throw this exception when the String accountName
     *                             does not exist for the profile in the database
     * @throws InsufficientBalance throw this exception when the subtractAmount
     *                             exceeds the balance in the Account
     */
    public void subtractBalance(String profileId, String accountName, Integer subtractAmount)
            throws ProfileNotFound, AccountNotFound, InsufficientBalance;

}

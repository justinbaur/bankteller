package org.justinbaur.bankteller.exceptions;

/**
 * Thrown to indicate that a profile has no existing accounts.
 */
public class NoExistingAccounts extends Exception {

    private static final long serialVersionUID = 1L;

    public NoExistingAccounts(String message) {
        super(message);
    }

}

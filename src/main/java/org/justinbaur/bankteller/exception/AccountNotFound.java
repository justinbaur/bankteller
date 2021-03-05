package org.justinbaur.bankteller.exception;

/**
 * Thrown to indicate that the specific account was not found.
 */
public class AccountNotFound extends Exception {

    private static final long serialVersionUID = 1L;

    public AccountNotFound(String message) {
        super(message);
    }

}

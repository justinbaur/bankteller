package org.justinbaur.bankteller.exceptions;

/**
 * Thrown to indicate that the balance in an account was insufficient for the
 * operation, such as withdrawal.
 */
public class InsufficientBalance extends Exception {

    private static final long serialVersionUID = 1L;

    public InsufficientBalance(String message) {
        super(message);
    }

}

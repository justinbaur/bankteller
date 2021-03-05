package org.justinbaur.bankteller.exception;

/**
 * Thrown to indicate that a specific profile was not found.
 */
public class ProfileNotFound extends Exception {

    private static final long serialVersionUID = 1L;

    public ProfileNotFound(String message) {
        super(message);
    }

}

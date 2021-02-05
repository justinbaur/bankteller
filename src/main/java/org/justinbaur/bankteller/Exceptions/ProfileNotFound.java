package org.justinbaur.bankteller.exceptions;

public class ProfileNotFound extends Exception {

    private static final long serialVersionUID = 1L;

    public ProfileNotFound(String message) {
        super(message);
    }

}

package org.justinbaur.bankteller.exceptions;

public class JsonWriteException extends Exception {

    private static final long serialVersionUID = 1L;

    public JsonWriteException(String message) {
        super(message);
    }

}

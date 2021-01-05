package org.justinbaur.bankteller.exceptions;

public class JsonReadException extends Exception {

    private static final long serialVersionUID = 1L;

    public JsonReadException(String message) {
        super(message);
    }

}

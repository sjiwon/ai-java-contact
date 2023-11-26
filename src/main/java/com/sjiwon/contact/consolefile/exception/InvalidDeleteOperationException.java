package com.sjiwon.contact.consolefile.exception;

public class InvalidDeleteOperationException extends RuntimeException {
    public InvalidDeleteOperationException() {
        super();
    }

    public InvalidDeleteOperationException(final String message) {
        super(message);
    }
}

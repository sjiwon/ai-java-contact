package com.sjiwon.contact.exception;

public class InvalidDeleteOperationException extends RuntimeException {
    public InvalidDeleteOperationException() {
        super();
    }

    public InvalidDeleteOperationException(final String message) {
        super(message);
    }
}

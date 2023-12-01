package com.sjiwon.contact.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException() {
        super();
    }

    public DuplicateResourceException(final String message) {
        super(message);
    }
}

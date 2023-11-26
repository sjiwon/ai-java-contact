package com.sjiwon.contact.consolefile.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException() {
        super();
    }

    public DuplicateResourceException(final String message) {
        super(message);
    }
}

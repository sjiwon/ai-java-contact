package com.sjiwon.contact.execute.consolefile.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException() {
        super();
    }

    public DuplicateResourceException(final String message) {
        super(message);
    }
}

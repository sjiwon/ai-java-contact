package com.sjiwon.contact.consolefile.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException() {
        super();
    }

    public InvalidNameException(final String message) {
        super(message);
    }
}

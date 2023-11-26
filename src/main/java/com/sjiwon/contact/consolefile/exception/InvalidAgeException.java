package com.sjiwon.contact.consolefile.exception;

public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException() {
        super();
    }

    public InvalidAgeException(final String message) {
        super(message);
    }
}

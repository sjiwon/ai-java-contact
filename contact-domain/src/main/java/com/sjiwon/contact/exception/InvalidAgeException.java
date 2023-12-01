package com.sjiwon.contact.exception;

public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException() {
        super();
    }

    public InvalidAgeException(final String message) {
        super(message);
    }
}

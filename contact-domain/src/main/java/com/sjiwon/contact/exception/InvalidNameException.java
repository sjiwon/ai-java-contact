package com.sjiwon.contact.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException() {
        super();
    }

    public InvalidNameException(final String message) {
        super(message);
    }
}

package com.sjiwon.contact.exception;

public class InvalidPhoneException extends RuntimeException {
    public InvalidPhoneException() {
        super();
    }

    public InvalidPhoneException(final String message) {
        super(message);
    }
}

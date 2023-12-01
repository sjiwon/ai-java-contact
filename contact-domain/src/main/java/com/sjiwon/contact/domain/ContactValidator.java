package com.sjiwon.contact.domain;

public class ContactValidator {
    public static boolean isValidNamePattern(final String value) {
        return Contact.NAME_PATTERN.matcher(value).matches();
    }

    public static boolean isValidPhonePattern(final String value) {
        return Contact.PHONE_PATTERN.matcher(value).matches();
    }

    public static boolean isValidAge(final String value) {
        return Integer.parseInt(value) > 0;
    }
}

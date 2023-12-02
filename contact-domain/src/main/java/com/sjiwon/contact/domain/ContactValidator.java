package com.sjiwon.contact.domain;

import java.util.regex.Pattern;

public class ContactValidator {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z]*$");

    public static boolean isInvalidNamePattern(final String value) {
        return !NAME_PATTERN.matcher(value).matches();
    }

    public static boolean isInvalidPhonePattern(final String value) {
        return !PHONE_PATTERN.matcher(value).matches();
    }

    public static boolean isInvalidAge(final String value) {
        return Integer.parseInt(value) <= 0;
    }
}

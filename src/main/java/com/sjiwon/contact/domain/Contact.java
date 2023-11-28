package com.sjiwon.contact.domain;

import java.time.LocalDateTime;

public record Contact(
        Long id,
        String name,
        int age,
        String phone,
        LocalDateTime createdAt
) {
    private static final String PHONE_DELIMITER = "-";
    public static final String FORM_DELIMITER = " ";

    public Contact(
            final String name,
            final int age,
            final String phone,
            final LocalDateTime createdAt
    ) {
        this(null, name, age, phone, createdAt);
    }

    public String getMediumPartOfPhone() {
        return phone.split(PHONE_DELIMITER)[1];
    }

    public String getLastPartOfPhone() {
        return phone.split(PHONE_DELIMITER)[2];
    }

    public String toForm() {
        return name + FORM_DELIMITER + age + FORM_DELIMITER + phone + FORM_DELIMITER + createdAt;
    }
}

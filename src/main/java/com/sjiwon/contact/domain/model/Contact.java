package com.sjiwon.contact.domain.model;

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

    /**
     * RDB Persist (with IDENTITY Strategy)
     */
    public Contact(final String name, final int age, final String phone) {
        this(null, name, age, phone, LocalDateTime.now());
    }

    public Contact(final Long id, final String name, final int age, final String phone) {
        this(id, name, age, phone, LocalDateTime.now());
    }

    public String getMediumPartOfPhone() {
        return phone.split(PHONE_DELIMITER)[1];
    }

    public String getLastPartOfPhone() {
        return phone.split(PHONE_DELIMITER)[2];
    }

    public String toForm() {
        return id + FORM_DELIMITER + name + FORM_DELIMITER + age + FORM_DELIMITER + phone + FORM_DELIMITER + createdAt;
    }
}

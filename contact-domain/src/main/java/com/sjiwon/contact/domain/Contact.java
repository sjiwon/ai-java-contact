package com.sjiwon.contact.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public record Contact(
        Long id,
        String name,
        int age,
        String phone,
        LocalDateTime createdAt
) {
    public static final String PHONE_DELIMITER = "-";
    public static final String FORM_DELIMITER = " ";
    public static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");
    public static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z]*$");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");

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

    public String organizeCreatedAt() {
        return createdAt.format(DATE_FORMAT);
    }
}

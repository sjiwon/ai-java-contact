package com.sjiwon.contact.domain;

public record Contact(
        Long id,
        String name,
        int age,
        String phone
) {
    private static final String PHONE_DELIMITER = "-";
    public static final String FORM_DELIMITER = " ";

    public Contact(
            final String name,
            final int age,
            final String phone
    ) {
        this(null, name, age, phone);
    }

    public String getMediumPartOfPhone() {
        return phone.split(PHONE_DELIMITER)[1];
    }

    public String getLastPartOfPhone() {
        return phone.split(PHONE_DELIMITER)[2];
    }

    public String toForm() {
        return name + FORM_DELIMITER + age + FORM_DELIMITER + phone;
    }
}

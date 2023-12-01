package com.sjiwon.contact;

import com.sjiwon.contact.domain.Contact;

import java.util.List;

public interface ContactReader {
    List<Contact> findAll();

    List<Contact> findByMedium(final String value);

    List<Contact> findByLast(final String value);

    Contact get(final Long id);

    boolean isPhoneUsedByOther(final String value);
}

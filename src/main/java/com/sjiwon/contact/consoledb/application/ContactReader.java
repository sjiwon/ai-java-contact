package com.sjiwon.contact.consoledb.application;

import com.sjiwon.contact.domain.Contact;

import java.util.List;

public interface ContactReader {
    List<Contact> findAll();

    boolean isPhoneUsedByOther(final String value);
}

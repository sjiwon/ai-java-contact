package com.sjiwon.contact.application;

import com.sjiwon.contact.ContactReader;
import com.sjiwon.contact.ContactWriter;
import com.sjiwon.contact.domain.Contact;
import com.sjiwon.contact.domain.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsoleRdbProcessor {
    private final ContactReader contactReader;
    private final ContactWriter contactWriter;

    public List<Contact> findAll() {
        return contactReader.findAll();
    }

    public List<Contact> findByCondition(final SearchType type, final String value) {
        return contactReader.findAll()
                .stream()
                .filter(contact -> type == SearchType.MIDDLE ? contact.getMediumPartOfPhone().contains(value) : contact.getLastPartOfPhone().contains(value))
                .toList();
    }

    public boolean isPhoneUsedByOther(final String value) {
        return contactReader.isPhoneUsedByOther(value);
    }

    public void create(final Contact contact) {
        contactWriter.create(contact);
    }

    public void delete(final Contact contact) {
        contactWriter.delete(contact);
    }
}

package com.sjiwon.contact.domain.application;

import com.sjiwon.contact.domain.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactGuiRdbInteractProcessor {
    private final ContactReader contactReader;
    private final ContactWriter contactWriter;

    public List<Contact> findAll() {
        return contactReader.findAll();
    }

    public List<Contact> findByMedium(final String value) {
        return contactReader.findByMedium(value);
    }

    public List<Contact> findByLast(final String value) {
        return contactReader.findByLast(value);
    }

    public Long create(final String name, final int age, final String phone) {
        return contactWriter.create(new Contact(name, age, phone)).id();
    }

    public void delete(final Long id) {
        final Contact contact = contactReader.get(id);
        contactWriter.delete(contact);
    }
}

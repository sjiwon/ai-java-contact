package com.sjiwon.contact.consoledb.application;

import com.sjiwon.contact.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactGuiActiveProcessor {
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

    public void create(final String name, final int age, final String phone) {
        contactWriter.create(new Contact(name, age, phone, LocalDateTime.now()));
    }

    public void delete(final Long id) {
        final Contact contact = contactReader.get(id);
        contactWriter.delete(contact);
    }
}

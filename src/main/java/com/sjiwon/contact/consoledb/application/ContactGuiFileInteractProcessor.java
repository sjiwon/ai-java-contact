package com.sjiwon.contact.consoledb.application;

import com.sjiwon.contact.consolefile.application.FileInteractor;
import com.sjiwon.contact.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;


@Component
@RequiredArgsConstructor
public class ContactGuiFileInteractProcessor {
    private final FileInteractor fileInteractor;

    public List<Contact> findAll() {
        return fileInteractor.read();
    }

    public List<Contact> findByMedium(final String value) {
        return fileInteractor.read()
                .stream()
                .filter(contact -> contact.getMediumPartOfPhone().contains(value))
                .toList();
    }

    public List<Contact> findByLast(final String value) {
        return fileInteractor.read()
                .stream()
                .filter(contact -> contact.getLastPartOfPhone().contains(value))
                .toList();
    }

    /**
     * RDB에 저장 후 id 받아서 invoke
     */
    public void create(final Long id, final String name, final int age, final String phone) {
        final Contact contact = new Contact(id, name, age, phone);

        fileInteractor.updateLastId(id);
        fileInteractor.writeWithKeepAdd(contact);
    }

    public void delete(final Long id) {
        final List<Contact> contacts = fileInteractor.read();
        if (contacts.isEmpty()) {
            return;
        }

        final List<Long> contactIds = contacts.stream()
                .map(Contact::id)
                .toList();
        if (!contactIds.contains(id)) {
            return;
        }

        final int removeIdx = getRemoveIdx(contactIds, id);
        if (removeIdx == -1) {
            return;
        }

        contacts.remove(removeIdx);
        fileInteractor.writeWithInit(contacts);
    }

    private int getRemoveIdx(final List<Long> contactIds, final Long id) {
        return IntStream.range(0, contactIds.size())
                .filter(i -> contactIds.get(i).equals(id))
                .findFirst()
                .orElse(-1);
    }
}

package com.sjiwon.contact.consoledb.application;

import com.sjiwon.contact.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sjiwon.contact.common.Constants.FILE_PATH;

@Component
@RequiredArgsConstructor
public class ContactGuiConsoleProcessor {
    private final ContactReader contactReader;

    public List<Contact> findAll() {
        final List<Contact> result = new ArrayList<>();

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH)))) {
            String line;

            while ((line = br.readLine()) != null) {
                final String[] token = line.split(Contact.FORM_DELIMITER);
                result.add(new Contact(
                        token[0],
                        Integer.parseInt(token[1]),
                        token[2],
                        LocalDateTime.now()
                ));
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<Contact> findByMedium(final String value) {
        final List<Contact> result = new ArrayList<>();

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH)))) {
            String line;

            while ((line = br.readLine()) != null) {
                final String[] token = line.split(Contact.FORM_DELIMITER);
                result.add(new Contact(
                        token[0],
                        Integer.parseInt(token[1]),
                        token[2],
                        LocalDateTime.now()
                ));
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return result.stream()
                .filter(contact -> contact.getMediumPartOfPhone().contains(value))
                .toList();
    }

    public List<Contact> findByLast(final String value) {
        final List<Contact> result = new ArrayList<>();

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH)))) {
            String line;

            while ((line = br.readLine()) != null) {
                final String[] token = line.split(Contact.FORM_DELIMITER);
                result.add(new Contact(
                        token[0],
                        Integer.parseInt(token[1]),
                        token[2],
                        LocalDateTime.now()
                ));
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return result.stream()
                .filter(contact -> contact.getLastPartOfPhone().contains(value))
                .toList();
    }

    public void create(final String name, final int age, final String phone) {
        final Contact contact = new Contact(name, age, phone, LocalDateTime.now());

        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, true)))) {
            final String text = contact.toForm();
            bw.write(text + "\n");
            bw.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(final Long id) {
        final List<Contact> contacts = contactReader.findAll();

        if (contacts.isEmpty()) {
            return;
        }

        final int index = contacts.stream()
                .map(Contact::id)
                .toList().indexOf(id);

        final List<Contact> fileContacts = findAll();
        fileContacts.remove(index);

        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, false)))) {
            for (final Contact contact : fileContacts) {
                bw.write(contact.toForm() + "\n");
            }

            bw.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}

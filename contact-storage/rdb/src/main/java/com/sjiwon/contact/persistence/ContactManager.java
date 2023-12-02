package com.sjiwon.contact.persistence;

import com.sjiwon.contact.ContactReader;
import com.sjiwon.contact.ContactWriter;
import com.sjiwon.contact.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class ContactManager implements ContactWriter, ContactReader {
    private final ContactJpaRepository contactJpaRepository;

    @Override
    public List<Contact> findAll() {
        return contactJpaRepository.findAll()
                .stream()
                .map(ContactMapper::toDomain)
                .toList();
    }

    @Override
    public List<Contact> findByMedium(final String value) {
        return contactJpaRepository.findAll()
                .stream()
                .map(ContactMapper::toDomain)
                .filter(contact -> contact.getMediumPartOfPhone().contains(value))
                .toList();
    }

    @Override
    public List<Contact> findByLast(final String value) {
        return contactJpaRepository.findAll()
                .stream()
                .map(ContactMapper::toDomain)
                .filter(contact -> contact.getLastPartOfPhone().contains(value))
                .toList();
    }

    @Override
    public Contact get(final Long id) {
        final ContactJpaEntity entity = contactJpaRepository.findById(id).orElseThrow();
        return ContactMapper.toDomain(entity);
    }

    @Override
    public boolean isPhoneUsedByOther(final String value) {
        return contactJpaRepository.existsByPhone(value);
    }

    @Override
    public Contact create(final Contact target) {
        final ContactJpaEntity entity = contactJpaRepository.save(ContactMapper.toEntity(target));
        return ContactMapper.toDomain(entity);
    }

    @Override
    public void delete(final Contact target) {
        contactJpaRepository.delete(ContactMapper.toEntity(target));
    }
}

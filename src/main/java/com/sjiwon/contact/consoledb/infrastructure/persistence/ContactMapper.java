package com.sjiwon.contact.consoledb.infrastructure.persistence;

import com.sjiwon.contact.domain.Contact;

public class ContactMapper {
    public static Contact toDomain(final ContactJpaEntity entity) {
        return new Contact(
                entity.getId(),
                entity.getName(),
                entity.getAge(),
                entity.getPhone(),
                entity.getCreatedAt()
        );
    }

    public static ContactJpaEntity toEntity(final Contact contact) {
        return new ContactJpaEntity(
                contact.id(),
                contact.name(),
                contact.age(),
                contact.phone(),
                contact.createdAt()
        );
    }
}

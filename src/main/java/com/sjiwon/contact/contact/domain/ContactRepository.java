package com.sjiwon.contact.contact.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByPhone(final String phone);
}

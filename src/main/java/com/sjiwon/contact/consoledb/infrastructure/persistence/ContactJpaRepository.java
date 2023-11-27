package com.sjiwon.contact.consoledb.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactJpaRepository extends JpaRepository<ContactJpaEntity, Long> {
    boolean existsByPhone(final String phone);
}

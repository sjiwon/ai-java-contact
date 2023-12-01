package com.sjiwon.contact.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface ContactJpaRepository extends JpaRepository<ContactJpaEntity, Long> {
    boolean existsByPhone(final String phone);
}

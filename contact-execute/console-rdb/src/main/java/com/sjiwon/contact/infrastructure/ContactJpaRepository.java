package com.sjiwon.contact.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

interface ContactJpaRepository extends JpaRepository<ContactJpaEntity, Long> {
    boolean existsByPhone(final String phone);
}

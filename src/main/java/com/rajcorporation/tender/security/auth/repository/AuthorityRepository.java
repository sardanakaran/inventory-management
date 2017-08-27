package com.rajcorporation.tender.security.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajcorporation.tender.security.auth.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}

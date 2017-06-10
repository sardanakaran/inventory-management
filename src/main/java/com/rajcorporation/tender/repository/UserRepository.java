package com.rajcorporation.tender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajcorporation.tender.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}

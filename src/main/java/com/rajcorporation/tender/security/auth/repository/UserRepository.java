package com.rajcorporation.tender.security.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajcorporation.tender.security.auth.model.User;

/**
 * Created by fan.jin on 2016-10-15.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
}


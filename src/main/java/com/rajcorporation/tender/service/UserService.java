package com.rajcorporation.tender.service;

import java.util.List;

import com.rajcorporation.tender.security.auth.model.User;

public interface UserService {
    public User findById(Long id);
    public User findByUsername(String username);
    public List<User> findAll ();
}

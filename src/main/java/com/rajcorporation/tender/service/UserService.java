package com.rajcorporation.tender.service;

import com.rajcorporation.tender.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}

package com.rajcorporation.tender.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}

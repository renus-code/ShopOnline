package com.humber.shop.service;

import com.humber.shop.model.WebUser;
import com.humber.shop.repository.WebUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebUserService {

    @Autowired
    private WebUserRepository webUserRepository;

    public void registerUser(WebUser user) {
        webUserRepository.save(user);
    }
}
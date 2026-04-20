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
        if (user.getState() == null) {
            user.setState("active");
        }
        webUserRepository.save(user);
    }

    public WebUser login(String loginId, String password) {
        WebUser user = webUserRepository.findById(loginId).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
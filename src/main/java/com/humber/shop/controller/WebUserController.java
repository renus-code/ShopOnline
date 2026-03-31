package com.humber.shop.controller;


import com.humber.shop.model.WebUser;
import com.humber.shop.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebUserController {

    @Autowired
    private WebUserService webUserService;

    @GetMapping("/register")
    public String registrationPage(Model model) {
        model.addAttribute("webUser", new WebUser());
        return "register";
    }

    // Handle the form submission (The Insert)
    @PostMapping("/register")
    public String saveUser(@ModelAttribute("webUser") WebUser webUser) {
        webUserService.registerUser(webUser);
        return "redirect:/register?success";
    }
}
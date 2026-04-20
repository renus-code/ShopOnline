package com.humber.shop.controller;


import com.humber.shop.model.WebUser;
import com.humber.shop.service.WebUserService;
import jakarta.servlet.http.HttpSession;
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
        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginId") String loginId,
                        @ModelAttribute("password") String password,
                        HttpSession session,
                        Model model) {
        WebUser user = webUserService.login(loginId, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid login ID or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
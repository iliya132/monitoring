package ru.iliya132.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.iliya132.model.User;
import ru.iliya132.service.security.UserDetailsServiceImpl;

@Controller
public class AuthController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registration() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@Validated User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "register";
        }
        try {
            userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
            userService.saveUser(userForm);
        } catch (Exception e) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "register";
        }

        return "redirect:/";
    }
}


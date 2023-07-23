package ru.iliya132.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

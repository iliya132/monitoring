package ru.iliya132.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.iliya132.model.dto.MonitorDto;
import ru.iliya132.service.MonitorService;

import java.security.Principal;

@Controller
public class UiController {
    MonitorService monitorService;
    public UiController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping(path = {"/", "/index"})
    public String index(Model model, Principal principal) {
        var userName = principal.getName();
        var userMonitors = monitorService.findForUser(userName)
                .stream()
                .map(MonitorDto::fromMonitor)
                .toList();
        model.addAttribute("monitors", userMonitors);
        return "index";
    }

    @GetMapping(path = "/create")
    public String create(Model model) {
        model.addAttribute("dto", new MonitorDto());
        return "create";
    }
}

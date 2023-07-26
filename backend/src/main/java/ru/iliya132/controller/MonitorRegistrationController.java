package ru.iliya132.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.iliya132.exception.InvalidMonitorException;
import ru.iliya132.model.Monitor;
import ru.iliya132.service.MonitorService;

import java.security.Principal;

@RestController
@RequestMapping("/api/monitor")
public class MonitorRegistrationController {
    @Autowired
    private MonitorService monitorService;

    @PostMapping("/register")
    public void register(@RequestBody Monitor monitor, Principal principal) {
        try {
            validateOrThrow(monitor);
            var userName = principal.getName();
            monitor.setOwner(userName);
            monitorService.save(monitor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "provided monitor is invalid");
        }
    }

    private void validateOrThrow(Monitor monitor) {
        boolean isCronExpressionInvalid = !CronExpression.isValidExpression(monitor.getCron());
        boolean isUrlInValid = monitor.getUrl() == null || monitor.getUrl().length() < 5;
        if (isCronExpressionInvalid) {
            throw new InvalidMonitorException("cron expression is invalid");
        }
        if (isUrlInValid) {
            throw new InvalidMonitorException("url provided is invalid");
        }
    }
}

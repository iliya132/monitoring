package ru.iliya132.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iliya132.exception.InvalidMonitorException;
import ru.iliya132.model.Monitor;
import ru.iliya132.service.MonitorService;

@RestController("/monitor")
public class MonitorController {
    @Autowired
    private MonitorService monitorService;
    @PostMapping
    public void register(Monitor monitor) {
        validateMonitor(monitor);
        monitorService.save(monitor);
    }

    private void validateMonitor(Monitor monitor) {
        boolean isValidCron = CronExpression.isValidExpression(monitor.getCron());
        boolean isValidOwner = monitor.getOwner() != null;
        boolean isValidUrl = monitor.getUrl() != null && monitor.getUrl().length() > 3;
        if(!(isValidUrl || isValidCron || isValidOwner)) {
            throw new InvalidMonitorException();
        }
    }
}

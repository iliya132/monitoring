package ru.iliya132.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iliya132.model.Monitor;
import ru.iliya132.model.MonitorType;
import ru.iliya132.processors.MonitorProcessor;
import ru.iliya132.service.MonitorService;

import java.util.HashMap;
import java.util.Map;

public class MonitoringExecutor implements Executor {
    private final MonitorService monitorService;
    private final Map<MonitorType, MonitorProcessor> processorMap = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(MonitoringExecutor.class);

    public MonitoringExecutor(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    public MonitoringExecutor registerProcessor(MonitorProcessor monitorProcessor) {
        processorMap.put(monitorProcessor.getType(), monitorProcessor);
        return this;
    }


    public void start() {
        var toExecute = monitorService.findAllForExecute();
        log.info("found {} monitors to execute", toExecute.size());
        toExecute.forEach(this::processMonitor);
    }

    private void processMonitor(Monitor monitor) {
        var processor = processorMap.get(monitor.getMonitorType());
        if (processor == null) {
            throw new RuntimeException("No processor registered for monitor_type: " + monitor.getMonitorType());
        }
        try {
            processor.process(monitor);
            monitorService.save(monitor);
        } catch (Exception e) {
            log.error("Exception while processing monitor.");
            throw new RuntimeException(e);
        }

    }
}

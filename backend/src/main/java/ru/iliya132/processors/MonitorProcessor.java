package ru.iliya132.processors;

import ru.iliya132.model.Monitor;
import ru.iliya132.model.MonitorType;
import ru.iliya132.service.MetricService;

import java.util.Map;

public abstract class MonitorProcessor {
    protected final MetricService metricService;

    public MonitorProcessor(MetricService metricService) {
        this.metricService = metricService;

    }

    public abstract MonitorType getType();

    public abstract void process(Monitor monitor);

    protected Map<String, String> mapToTags(Monitor monitor) {
        return Map.of("system", monitor.getService(),
                "type", monitor.getMonitorType().name());
    }
}

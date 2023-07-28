package ru.iliya132.processors;

import org.influxdb.InfluxDB;
import ru.iliya132.model.Monitor;
import ru.iliya132.model.MonitorType;
import ru.iliya132.service.MetricService;

public abstract class MonitorProcessor {
    protected final MetricService metricService;

    public  MonitorProcessor(MetricService metricService) {
        this.metricService = metricService;

    }
    public abstract MonitorType getType();

    public abstract void process(Monitor monitor);
}

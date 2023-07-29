package ru.iliya132.service;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iliya132.model.Metric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class MetricService {
    private final InfluxDB influxDB;
    private Logger log = LoggerFactory.getLogger(MetricService.class);

    public MetricService(InfluxDB influxDB, String influxDatabase) {
        this.influxDB = influxDB;
        influxDB.setDatabase(influxDatabase);
        influxDB.enableBatch(100, 5, TimeUnit.SECONDS);
    }

    public void write(Metric metric) {
        inTransaction(tr -> tr.write(metric));
    }

    public void write(Collection<Metric> metrics) {
        inTransaction(tr -> tr.write(metrics));
    }

    public void flush() {
        influxDB.flush();
    }

    // Атомарность не гарантирована, но и не критична
    private void inTransaction(Consumer<MetricTransaction> o) {
        var transaction = new MetricTransaction();
        o.accept(transaction);
        var metrics = transaction.extract();
        metrics.stream()
                .map(it -> Point
                        .measurementByPOJO(Metric.class)
                        .addFieldsFromPOJO(it)
                        .tag(it.getTags())
                        .build())
                .forEach(it -> {
                    log.info("writing {} to influx", it);
                    influxDB.write(it);
                }); // retention enabled - this is batched operation
    }

    static class MetricTransaction {
        private final List<Metric> metrics = new ArrayList<>();

        public void write(Metric metric) {
            metrics.add(metric);
        }

        public void write(Collection<Metric> metrics) {
            this.metrics.addAll(metrics);
        }

        public List<Metric> extract() {
            return metrics;
        }
    }
}

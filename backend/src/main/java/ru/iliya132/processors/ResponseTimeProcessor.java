package ru.iliya132.processors;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iliya132.model.Metric;
import ru.iliya132.model.Monitor;
import ru.iliya132.model.MonitorType;
import ru.iliya132.service.MetricService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class ResponseTimeProcessor extends MonitorProcessor {
    private HttpClient httpClient = HttpClient.newHttpClient();
    private final Logger log = LoggerFactory.getLogger(ResponseTimeProcessor.class);

    public ResponseTimeProcessor(MetricService metricService) {
        super(metricService);
    }

    @Override
    public MonitorType getType() {
        return MonitorType.RESPONSE_TIME;
    }

    @Override
    public void process(Monitor monitor) {
        var request = HttpRequest.newBuilder().GET()
                .uri(URI.create(monitor.getUrl()))
                .build();
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var elapsed = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            Metric metric = Metric.builder()
                    .owner(monitor.getOwner())
                    .tags(mapToTags(monitor))
                    .numericValue((double) elapsed)
                    .build();
            metricService.write(metric);
        } catch (Exception e) {
            log.error("Exception while measuring response time of: {}", monitor.getUrl());
            throw new RuntimeException(e);
        }
    }
}

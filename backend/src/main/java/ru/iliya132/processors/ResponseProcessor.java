package ru.iliya132.processors;

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

public class ResponseProcessor extends MonitorProcessor {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Logger log = LoggerFactory.getLogger(ResponseTimeProcessor.class);

    public ResponseProcessor(MetricService metricService) {
        super(metricService);
    }

    @Override
    public MonitorType getType() {
        return MonitorType.RESPONSE;
    }

    @Override
    public void process(Monitor monitor) {
        var request = HttpRequest.newBuilder().GET()
                .uri(URI.create(monitor.getUrl()))
                .build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Metric metric = Metric.builder()
                    .owner(monitor.getOwner())
                    .tags(mapToTags(monitor))
                    .strValue(response.body())
                    .build();
            metricService.write(metric);
        } catch (Exception e) {
            log.error("Exception while measuring response time of: {}", monitor.getUrl());
            throw new RuntimeException(e);
        }
    }
}

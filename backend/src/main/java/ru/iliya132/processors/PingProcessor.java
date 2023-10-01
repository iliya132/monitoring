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

public class PingProcessor extends MonitorProcessor {
    private HttpClient httpClient = HttpClient.newHttpClient();
    private final Logger log = LoggerFactory.getLogger(PingProcessor.class);

    public PingProcessor(MetricService metricService) {
        super(metricService);
    }

    @Override
    public MonitorType getType() {
        return MonitorType.PING;
    }

    @Override
    public void process(Monitor monitor) {
        var request = HttpRequest.newBuilder().GET()
                .uri(URI.create(monitor.getUrl()))
                .build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Successfully pinged {}", monitor.getUrl());
            Metric metric = Metric.builder()
                    .owner(monitor.getOwner())
                    .tags(mapToTags(monitor))
                    .numericValue((double) response.statusCode())
                    .build();
            metricService.write(metric);
        } catch (Exception e) {
            log.error("Exception while pinging url: {}", monitor.getUrl());
            throw new RuntimeException(e);
        }
    }
}

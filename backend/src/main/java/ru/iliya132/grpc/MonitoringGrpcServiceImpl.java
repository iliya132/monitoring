package ru.iliya132.grpc;

import io.grpc.stub.StreamObserver;
import ru.iliya132.model.Metric;
import ru.iliya132.monitoring.protobuf.generated.MetricRequest;
import ru.iliya132.monitoring.protobuf.generated.RemoteMonitorServiceGrpc;
import ru.iliya132.monitoring.protobuf.generated.Tag;
import ru.iliya132.service.MetricService;

import java.time.Instant;
import java.util.stream.Collectors;

public class MonitoringGrpcServiceImpl extends RemoteMonitorServiceGrpc.RemoteMonitorServiceImplBase {

    private final MetricService metricService;

    public MonitoringGrpcServiceImpl(MetricService metricService) {
        this.metricService = metricService;
    }

    @Override
    public void push(MetricRequest request,
                     StreamObserver<ru.iliya132.monitoring.protobuf.generated.Empty> responseObserver) {
        metricService.write(mapFromProto(request));
        responseObserver.onCompleted();
    }

    private Metric mapFromProto(MetricRequest metric) {
        return Metric.builder()
                .owner(metric.getOwner())
                .numericValue(metric.getNumeric())
                .strValue(metric.getStrValue())
                .time(Instant.ofEpochMilli(metric.getTimestamp()))
                .tags(metric.getTagsList().stream().collect(Collectors.toMap(Tag::getKey, Tag::getValue)))
                .build();
    }
}

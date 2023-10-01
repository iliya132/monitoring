package ru.iliya132.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    Server server;

    public GrpcServer(int port,
                      MonitoringGrpcServiceImpl monitoringGrpcService) {
        server = ServerBuilder
                .forPort(port)
                .addService(monitoringGrpcService)
                .build();
    }

    public void start() throws IOException {
        server.start();
    }

    public void shutDown() {
        server.shutdown();
    }
}

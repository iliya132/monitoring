package ru.iliya132.service;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.support.CronExpression;
import ru.iliya132.model.Monitor;
import ru.iliya132.repository.IMonitorRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;

public class MonitorService {
    private final IMonitorRepository monitorRepository;

    public MonitorService(IMonitorRepository monitorRepository){
        this.monitorRepository = monitorRepository;
    }

    public void save(Monitor monitor) {
        monitor.setNextRun(calculateNextRun(monitor.getCron()));
        monitorRepository.save(monitor);
    }

    public void update(Monitor monitor) {
        update(List.of(monitor));
    }

    public void update(Collection<Monitor> monitors) {
        monitors.forEach(it -> {
            it.setNextRun(calculateNextRun(it.getCron()));
        });
        monitorRepository.saveAll(monitors);
    }

    public List<Monitor> findForUser(@NotNull String userId) {
        return monitorRepository.findByUserId(userId);
    }

    public List<Monitor> findAllForExecute() {
        return monitorRepository.findAllForExecution();
    }

    private Instant calculateNextRun(String cron) {
        CronExpression cronExpression = CronExpression.parse(cron);
        return cronExpression.next(LocalDateTime.now())
                .toInstant(ZoneOffset.UTC)
                .minus(3, ChronoUnit.HOURS);
    }

    public void delete(Long monitorId) {
        monitorRepository.deleteById(monitorId);
    }
}

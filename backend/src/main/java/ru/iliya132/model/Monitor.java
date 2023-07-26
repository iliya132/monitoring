package ru.iliya132.model;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.Instant;

@Entity
@Table(name = "monitors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Monitor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String owner;
    @Column(name = "cron_expression")
    private String cron;
    private String description;
    @Enumerated(EnumType.STRING)
    @Type(value = PostgreSQLEnumType.class)
    @Column(name = "monitor_type")
    private MonitorType monitorType;
    private String url;
    @Column(name = "last_run")
    private Instant lastRun;
    @Column(name = "next_run")
    private Instant nextRun;
}

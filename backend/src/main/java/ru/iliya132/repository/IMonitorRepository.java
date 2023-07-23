package ru.iliya132.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.iliya132.model.Monitor;

import java.util.List;

public interface IMonitorRepository extends CrudRepository<Monitor, Long> {
    @Query(nativeQuery = true, value = "select * from monitors where next_run <= now()")
    List<Monitor> findAllForExecution();
}

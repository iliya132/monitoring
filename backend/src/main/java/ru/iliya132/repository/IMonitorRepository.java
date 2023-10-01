package ru.iliya132.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.iliya132.model.Monitor;

import java.util.List;

public interface IMonitorRepository extends CrudRepository<Monitor, Long> {
    @Query(nativeQuery = true, value = "select * from monitors where next_run <= now()")
    List<Monitor> findAllForExecution();

    @Query(nativeQuery = true, value = "select * from monitors where owner = :user_id")
    List<Monitor> findByUserId(@Param("user_id") String userId);
}

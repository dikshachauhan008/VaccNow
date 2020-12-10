package com.xebia.vaccnow.repository;

import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.model.Schedule;
import com.xebia.vaccnow.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByBranch(Branch branch);

    List<Schedule> findByStatus(Status status);
}

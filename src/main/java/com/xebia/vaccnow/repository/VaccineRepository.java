package com.xebia.vaccnow.repository;

import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByBranch(Branch branch);
}

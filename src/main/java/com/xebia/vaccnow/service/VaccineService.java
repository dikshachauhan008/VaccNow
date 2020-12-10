package com.xebia.vaccnow.service;

import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.model.Vaccine;
import com.xebia.vaccnow.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;


    public Vaccine save(Vaccine vaccine) {
        return vaccineRepository.save(vaccine);
    }

    public List<Vaccine> findByBranch(Branch branch) {
       return vaccineRepository.findByBranch(branch);
    }
}

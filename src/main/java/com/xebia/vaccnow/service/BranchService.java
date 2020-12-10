package com.xebia.vaccnow.service;

import com.xebia.vaccnow.api.dto.BranchDTO;
import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    @Autowired
    private BranchRepository branchRepository;

    public List<Branch> findAllBranches() {
        return branchRepository.findAll();
    }

    public Optional<Branch> findById(Long id) {
        return branchRepository.findById(id);
    }

    public Branch save(Branch branch1) {
        return branchRepository.save(branch1);
    }

    public BranchDTO getAvailableTimeForBranch(Long id) {
        Branch branch = null;
        BranchDTO branchDTO = new BranchDTO();
        Optional<Branch> branchOptional = branchRepository.findById(id);
        if (branchOptional.isPresent()) {
            branch = branchOptional.get();
            branchDTO.setVistingStartTime(branch.getVistingStartTime());
            branchDTO.setVisitingEndTime(branch.getVisitingEndTime());
        }
        return branchDTO;
    }

    public BranchDTO getSpecificAvailabilityByBranch(Long id, String visitTime) {
        BranchDTO result = new BranchDTO();
        Branch branch = null;
        Optional<Branch> branchOptional = branchRepository.findById(id);
        if (branchOptional.isPresent()) {
            branch = branchOptional.get();
            result = checkAvailable(branch, visitTime);
        }
        return result;
    }

    private BranchDTO checkAvailable(Branch branch, String visitTime) {
        Boolean flag = false;
        String[] visitingTime = visitTime.split(":");
        LocalTime time = LocalTime.of(Integer.parseInt(visitingTime[0]), Integer.parseInt(visitingTime[1])
                , Integer.parseInt(visitingTime[2]));
        if (time.isAfter(branch.getVistingStartTime()) && time.isBefore(branch.getVisitingEndTime())) {
            flag = true;
        }
        return new BranchDTO(flag);
    }

}

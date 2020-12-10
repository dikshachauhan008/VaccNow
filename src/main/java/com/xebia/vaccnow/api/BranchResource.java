package com.xebia.vaccnow.api;

import com.xebia.vaccnow.api.dto.BranchDTO;
import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchResource {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<List<Branch>> listBranches() {
        List<Branch> branchList = branchService.findAllBranches();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(branchList);
    }

    @GetMapping("/getSpecificAvailability/{branchId}")
    public ResponseEntity<BranchDTO> getSpecificAvailabilityByBranch(@PathVariable(value = "branchId")
                                                                             Long id, @RequestParam(name = "visitTime") String visitTime) {
        BranchDTO result = branchService.getSpecificAvailabilityByBranch(id, visitTime);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/getAvailableTime/{id}")
    public ResponseEntity<BranchDTO> getAvailableTimeForBranch(@PathVariable(value = "id")
                                                                       Long id) {
        BranchDTO result = branchService.getAvailableTimeForBranch(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}

package com.xebia.vaccnow.api;

import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.model.Vaccine;
import com.xebia.vaccnow.service.BranchService;
import com.xebia.vaccnow.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class VaccineResource {

    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private BranchService branchService;

    @PostMapping(path = "/{id}/vaccines")
    public ResponseEntity<Vaccine> createVaccine(@PathVariable(value = "id")
                                                         Long id,
                                                 @RequestBody Vaccine vaccine) {
        Branch branch = branchService.findById(id).get();
        vaccine.setBranch(branch);
        Vaccine savedVaccine = vaccineService.save(vaccine);
        return new ResponseEntity<Vaccine>(savedVaccine, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}/vaccines")
    public ResponseEntity<List<Vaccine>> listOfVaccinesByBranchId(@PathVariable(value = "id")
                                                                              Long id) {
        Branch branch = branchService.findById(id).get();
        List<Vaccine> vaccineList = vaccineService.findByBranch(branch);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vaccineList);
    }
}

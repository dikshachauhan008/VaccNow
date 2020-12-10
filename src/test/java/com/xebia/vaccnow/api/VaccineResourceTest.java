package com.xebia.vaccnow.api;

import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.model.Vaccine;
import com.xebia.vaccnow.service.BranchService;
import com.xebia.vaccnow.service.VaccineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalTime;

@SpringBootTest
@AutoConfigureMockMvc
public class VaccineResourceTest {

    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_list_of_vaccines_for_a_particular_branch() throws Exception {
        Branch branch1 = new Branch.BranchBuilder()
                .withName("branch1")
                .withId(123L)
                .withVistingStartTime(LocalTime.of(07, 00, 00))
                .withVisitingEndTime(LocalTime.of(18, 00, 00))
                .build();
        Branch branchSaved = branchService.save(branch1);
        Vaccine vaccine1 = new Vaccine(11L, "Vaxchora",500);
        Vaccine vaccine2 = new Vaccine(12L, "LAIV",500);
        vaccine1.setBranch(branchSaved);
        vaccine2.setBranch(branchSaved);
        Vaccine savedVaccine = vaccineService.save(vaccine1);
        Vaccine savedVaccine2 = vaccineService.save(vaccine2);
        int count = (vaccineService.findByBranch(branchSaved)).size();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/branches/{id}/vaccines", branchSaved.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(count));
    }
}
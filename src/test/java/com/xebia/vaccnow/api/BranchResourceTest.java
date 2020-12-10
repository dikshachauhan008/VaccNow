package com.xebia.vaccnow.api;

import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.repository.BranchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BranchResourceTest {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        branchRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        branchRepository.deleteAll();
    }

    @Test
    void should_list_all_branches() throws Exception {
        Branch branch1 = new Branch.BranchBuilder()
                .withName("branch1")
                .withId(1L)
                .withVistingStartTime(LocalTime.of(07, 0, 0))
                .withVisitingEndTime(LocalTime.of(18, 0, 0))
                .build();
        Branch branch2 = new Branch.BranchBuilder()
                .withName("branch2")
                .withId(2L)
                .withVistingStartTime(LocalTime.of(07, 0, 0))
                .withVisitingEndTime(LocalTime.of(18, 0, 0))
                .build();
        int beforeCount = branchRepository.findAll().size();
        branchRepository.save(branch2);
        branchRepository.save(branch1);
        this.mockMvc.perform(get("/api/branches"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(beforeCount + 2));
    }

    @Test
    void should_get_specific_availability_as_true() throws Exception {
        Branch branch1 = new Branch.BranchBuilder()
                .withName("branch1")
                .withId(1L)
                .withVistingStartTime(LocalTime.of(07, 0, 0))
                .withVisitingEndTime(LocalTime.of(18, 0, 0))
                .build();
        Branch branch = branchRepository.save(branch1);
        String visitTime = "09:00:00";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/branches/getSpecificAvailability/{branchId}", branch.getId())
                .param("visitTime", visitTime))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.branchStatus").value(true));
    }

    @Test
    void should_get_specific_availability_as_false() throws Exception {
        Branch branch1 = new Branch.BranchBuilder()
                .withName("branch1")
                .withId(1L)
                .withVistingStartTime(LocalTime.of(07, 00, 00))
                .withVisitingEndTime(LocalTime.of(18, 00, 00))
                .build();
        Branch branch = branchRepository.save(branch1);
        String visitTime = "21:00:00";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/branches/getSpecificAvailability/{branchId}", branch.getId())
                .param("visitTime", visitTime))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.branchStatus").value(false));
    }

    @Test
    void should_get_available_time_for_branch() throws Exception {
        Branch branch1 = new Branch.BranchBuilder()
                .withName("branch1")
                .withId(1L)
                .withVistingStartTime(LocalTime.of(07, 1, 1))
                .withVisitingEndTime(LocalTime.of(18, 0, 0))
                .build();
        Branch branch = branchRepository.save(branch1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/branches/getAvailableTime/{id}", branch.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.vistingStartTime").isNotEmpty())
                .andExpect(jsonPath("$.visitingEndTime").isNotEmpty());
    }
}
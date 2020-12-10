package com.xebia.vaccnow.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.vaccnow.api.dto.ScheduleDTO;
import com.xebia.vaccnow.api.dto.ScheduleRequestDTO;
import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.model.Status;
import com.xebia.vaccnow.model.Vaccine;
import com.xebia.vaccnow.repository.BranchRepository;
import com.xebia.vaccnow.repository.ScheduleRepository;
import com.xebia.vaccnow.repository.VaccineRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleResourceTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Branch branch;
    private Vaccine vaccine;

    @AfterEach
    void tearDown() {
        branchRepository.deleteAll();
        vaccineRepository.deleteAll();
        scheduleRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        Branch branch1 = new Branch.BranchBuilder()
                .withName("branch1")
                .withId(1L)
                .withVistingStartTime(LocalTime.of(07, 0, 0))
                .withVisitingEndTime(LocalTime.of(18, 0, 0))
                .build();
        branch = branchRepository.save(branch1);
        Vaccine vaccine1 = new Vaccine(11L, "Vaxchora",500);
        vaccine1.setBranch(branch);
        vaccine = vaccineRepository.save(vaccine1);
    }

    @Test
    public void should_create_a_schedule() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO.ScheduleDTOBuilder().withBranchId(branch.getId())
                                    .withVaccineId(vaccine.getId())
                                    .withEmailId("dikshachauhan008@gmail.com")
                                    .withLocalDateTime(LocalDateTime.now()).build();
        String json = objectMapper.writeValueAsString(scheduleDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/schedule")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void should_confirm_a_schedule_vaccination_by_email() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO.ScheduleDTOBuilder().withBranchId(branch.getId())
                .withVaccineId(vaccine.getId())
                .withEmailId("dikshachauhan008@gmail.com")
                .withLocalDateTime(LocalDateTime.now()).build();
        String json = objectMapper.writeValueAsString(scheduleDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/schedule")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void should_get_list_of_schedule_by_branch() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO.ScheduleDTOBuilder().withBranchId(branch.getId())
                .withVaccineId(vaccine.getId())
                .withEmailId("diksha.chauhan@xebia.com")
                .withLocalDateTime(LocalDateTime.now()).build();
        String json = objectMapper.writeValueAsString(scheduleDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/schedule")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        int count = scheduleRepository.findByBranch(branch).size();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/schedule/branch/{branchId}",branch.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(count));
    }

    @Test
    public void should_get_list_of_schedule_by_branch_per_time_period_of_day() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = simpleDateFormat.parse("10-12-2020");
        ScheduleDTO scheduleDTO = new ScheduleDTO.ScheduleDTOBuilder().withBranchId(branch.getId())
                .withVaccineId(vaccine.getId())
                .withEmailId("diksha.chauhan@xebia.com")
                .withLocalDateTime(LocalDateTime.now()).build();
        String json = objectMapper.writeValueAsString(scheduleDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/schedule")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        int count = scheduleRepository.findByBranch(branch).size();
        ScheduleRequestDTO scheduleRequestDTO =new ScheduleRequestDTO(date,LocalTime.of(8,0,0),LocalTime.of(10,0,0));
        String json1 = objectMapper.writeValueAsString(scheduleRequestDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/schedule/branch/byPeriod/{branchId}", branch.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(json1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }

    @Test
    public void should_get_list_of_confirmed_schedule_over_a_time_period() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = simpleDateFormat.parse("10-12-2020");
        ScheduleDTO scheduleDTO = new ScheduleDTO.ScheduleDTOBuilder().withBranchId(branch.getId())
                .withVaccineId(vaccine.getId())
                .withEmailId("diksha.chauhan@xebia.com")
                .withLocalDateTime(LocalDateTime.now()).build();
        scheduleDTO.setStatus(Status.CONFIRM);
        String json = objectMapper.writeValueAsString(scheduleDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/schedule")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Date fromDate = simpleDateFormat.parse("10-12-2020");
        Date toDate = simpleDateFormat.parse("20-12-2020");
        ScheduleRequestDTO scheduleRequestDTO =new ScheduleRequestDTO(LocalDateTime.of(2020,12,10,0,0,0),LocalDateTime.of(2020,12,20,0,0,0));
        String json1 = objectMapper.writeValueAsString(scheduleRequestDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/schedule/byPeriod")
                .accept(MediaType.APPLICATION_JSON)
                .content(json1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }

}
package com.xebia.vaccnow.api;

import com.xebia.vaccnow.api.dto.ScheduleDTO;
import com.xebia.vaccnow.api.dto.ScheduleRequestDTO;
import com.xebia.vaccnow.model.Schedule;
import com.xebia.vaccnow.service.EmailService;
import com.xebia.vaccnow.service.GmailService;
import com.xebia.vaccnow.service.InvoiceGenerationService;
import com.xebia.vaccnow.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleResource {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmailService gmailService;

    @Autowired
    private InvoiceGenerationService invoiceGenerationService;

    @PostMapping
    public ResponseEntity<Schedule> create(@RequestBody ScheduleDTO scheduleRequest) throws ExecutionException, InterruptedException {
        Schedule scheduleSaved = scheduleService.save(scheduleRequest);
        gmailService.sendMail(scheduleSaved.getEmailId(),scheduleSaved);
        return new ResponseEntity<Schedule>(scheduleSaved, HttpStatus.CREATED);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Schedule>> getListOfAppliedVaccinationPerBranch(@PathVariable(value = "branchId")
                                                                                       Long id) {
        List<Schedule> scheduleList = scheduleService.findByBranch(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleList);
    }

    @PostMapping("/branch/byPeriod/{branchId}")
    public ResponseEntity<List<Schedule>> getListOfAppliedVaccinationPerBranchPerDayPeriod(@PathVariable(value = "branchId")
                                                                                                   Long id, @RequestBody ScheduleRequestDTO scheduleRequestDTO) throws ParseException {
        List<Schedule> scheduleList = scheduleService.getListOfAppliedVaccinationPerBranchPerDayPeriod(id, scheduleRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleList);
    }

    @PostMapping("/byPeriod")
    public ResponseEntity<List<Schedule>> getAllConfirmedVaccinationOverATimePeriod( @RequestBody ScheduleRequestDTO scheduleRequestDTO) throws ParseException {
        List<Schedule> scheduleList = scheduleService.getAllConfirmedVaccinationOverATimePeriod(scheduleRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleList);
    }

    @PostMapping("/generateInvoice/{scheduleId}")
    public ResponseEntity<Schedule> generateInvoice(@PathVariable(value = "scheduleId")
                                                                Long id) throws IOException, URISyntaxException {
        Schedule schedule = invoiceGenerationService.generateInvoice(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(schedule);
    }

}

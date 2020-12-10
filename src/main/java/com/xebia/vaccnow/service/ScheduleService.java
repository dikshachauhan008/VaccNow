package com.xebia.vaccnow.service;

import com.xebia.vaccnow.api.dto.ScheduleDTO;
import com.xebia.vaccnow.api.dto.ScheduleRequestDTO;
import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.model.Schedule;
import com.xebia.vaccnow.model.Status;
import com.xebia.vaccnow.model.Vaccine;
import com.xebia.vaccnow.repository.BranchRepository;
import com.xebia.vaccnow.repository.ScheduleRepository;
import com.xebia.vaccnow.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    public Schedule save(ScheduleDTO scheduleRequest) {
        Schedule schedule = new Schedule();
        Long branchId = scheduleRequest.getBranchId();
        Long vaccineId = scheduleRequest.getVaccineId();
        Vaccine vaccine = null;
        Branch branch = null;
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if(optionalBranch.isPresent())
        {
            branch = optionalBranch.get();
            Optional<Vaccine> optionalVaccine = vaccineRepository.findById(vaccineId);
            if(optionalVaccine.isPresent()) {
                vaccine = optionalVaccine.get();
                List<Vaccine> byBranch = vaccineRepository.findByBranch(branch);
                if(byBranch.contains(vaccine))
                {
                    LocalTime endTime = branch.getVisitingEndTime().plusMinutes(15);
                    LocalDateTime localDateTime = scheduleRequest.getLocalDateTime();
                    LocalTime requestedTime = LocalTime.of(localDateTime.getHour(),localDateTime.getMinute(),localDateTime.getSecond());
                    if(requestedTime.isAfter(branch.getVistingStartTime()) || requestedTime.isBefore(endTime)) {
                        schedule.setBranch(branch);
                    }
                    else
                    {
                        throw new IllegalArgumentException("Schedule time is not between branch visting hours");
                    }
                    schedule.setVaccine(vaccine);
                    schedule.setEmailId(scheduleRequest.getEmailId());
                    schedule.setDateTime(scheduleRequest.getLocalDateTime());
                    schedule.setStatus(scheduleRequest.getStatus());
                    schedule = scheduleRepository.save(schedule);
                }
            }
            else
            {
                throw new IllegalArgumentException("Vaccine Id is invalid");
            }
        }
        else {
            throw new IllegalArgumentException("Branch Id is invalid");
        }
        return schedule;
    }

    public List<Schedule> findByBranch(Long id) {
        List<Schedule> scheduleList = new ArrayList<>();
        Branch branch = null;
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if(optionalBranch.isPresent())
        {
            branch = optionalBranch.get();
            List<Schedule> schedules = scheduleRepository.findByBranch(branch);
            scheduleList.addAll(schedules);
        }
        return scheduleList;
    }

    public List<Schedule> getListOfAppliedVaccinationPerBranchPerDayPeriod(Long id, ScheduleRequestDTO scheduleRequestDTO) throws ParseException {
        List<Schedule> scheduleList = new ArrayList<>();
        Branch branch = null;
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if(optionalBranch.isPresent())
        {
            branch = optionalBranch.get();
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            String date = dateFormat.format(scheduleRequestDTO.getDate());
            String[] dateData = date.split("-");
            LocalDateTime requestStartDateTime = LocalDateTime.of(Integer.parseInt(dateData[2]),Integer.parseInt(dateData[1])
                    ,Integer.parseInt(dateData[0]),scheduleRequestDTO.getStartTime().getHour(),scheduleRequestDTO.getStartTime().getMinute(),scheduleRequestDTO.getStartTime().getSecond());
            LocalDateTime requestEndDateTime = LocalDateTime.of(Integer.parseInt(dateData[2]),Integer.parseInt(dateData[1])
                    ,Integer.parseInt(dateData[0]),scheduleRequestDTO.getEndTime().getHour(),scheduleRequestDTO.getEndTime().getMinute(),scheduleRequestDTO.getEndTime().getSecond());
            List<Schedule> schedules = scheduleRepository.findByBranch(branch);
            for (Schedule schedule: schedules
                 ) {
                if(schedule.getDateTime().isAfter(requestStartDateTime) || schedule.getDateTime().isBefore(requestEndDateTime))
                {
                    scheduleList.add(schedule);
                }
            }
        }
        return scheduleList;
    }

    public List<Schedule> getAllConfirmedVaccinationOverATimePeriod(ScheduleRequestDTO scheduleRequestDTO){
        List<Schedule> scheduleList = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findByStatus(Status.CONFIRM);
        for (Schedule schedule : schedules) {
            if(schedule.getDateTime().isAfter(scheduleRequestDTO.getFromDate()) && schedule.getDateTime().isBefore(scheduleRequestDTO.getToDate()))
            {
                scheduleList.add(schedule);
            }
        }
        return scheduleList;

    }
}

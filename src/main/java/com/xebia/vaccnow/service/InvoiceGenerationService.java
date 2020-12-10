package com.xebia.vaccnow.service;

import com.xebia.vaccnow.model.Branch;
import com.xebia.vaccnow.model.Schedule;
import com.xebia.vaccnow.model.Vaccine;
import com.xebia.vaccnow.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

@Component
public class InvoiceGenerationService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Value("${report.io.dir.path}")
    private String basePath;

    public Schedule generateInvoice(Long id) throws IOException, URISyntaxException {
        Schedule schedule = null;
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if(optionalSchedule.isPresent())
        {
            schedule = optionalSchedule.get();
            Branch branch = schedule.getBranch();
            Vaccine vaccine = schedule.getVaccine();
            writeFile(branch,vaccine,schedule);
        }
        return schedule;
    }

    private void writeFile(Branch branch, Vaccine vaccine, Schedule schedule) throws IOException, URISyntaxException {
       StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Welcome to " +branch.getName());
            stringBuilder.append("\n");
        stringBuilder.append("Thanks for scheduling vaacination for vaccine " +vaccine.getName() +" with us");
        stringBuilder.append("\n");
        stringBuilder.append("It is to certify that You have completed the vaccination");
        stringBuilder.append("\n");
        stringBuilder.append("Your Bill");
        stringBuilder.append("\n");
        stringBuilder.append("Vaccination Name:" + vaccine.getName());
        stringBuilder.append("\n");
        stringBuilder.append("Total Bill:" + vaccine.getPrice());
        writeUsingOutputStream(stringBuilder.toString());
    }
    private void writeUsingOutputStream(String data) {
        OutputStream os = null;
        try {
            UUID random = UUID.randomUUID();
            StringBuilder fileName = new StringBuilder(basePath);
            fileName.append("/invoice");
            fileName.append(UUID.randomUUID().toString()+".txt");
            os = new FileOutputStream(new File(fileName.toString()));
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

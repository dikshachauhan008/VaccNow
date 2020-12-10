package com.xebia.vaccnow.service;

import com.xebia.vaccnow.model.Schedule;
import com.xebia.vaccnow.model.Status;
import com.xebia.vaccnow.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
@Profile("!test")
public class GmailService implements EmailService {
    @Value("${spring.mail.gmail.subject}")
    private String subject;
    @Value("${spring.mail.gmail.content}")
    private String content;
    @Autowired
    public JavaMailSender javaMailSender;
    @Autowired
    ExecutorService executorService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void sendMail(String emailAddress, Schedule schedule) throws ExecutionException, InterruptedException {
        Boolean flag = false;
        Future<?> future =  this.executorService.submit(() -> {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(emailAddress);
            mailMessage.setSubject(subject);
            mailMessage.setText(content);
            javaMailSender.send(mailMessage);
        });
        System.out.println(future.get());
        if(future.isDone())
        {
            schedule.setStatus(Status.CONFIRM);
            scheduleRepository.save(schedule);
        }
    }
}

package com.xebia.vaccnow.service;

import com.xebia.vaccnow.model.Schedule;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Profile("test")
@Component
public class NoMailService implements  EmailService{
    @Override
    public void sendMail(String emailAddress, Schedule schedule) throws ExecutionException, InterruptedException {
        System.out.println("Test Mail service");
    }
}

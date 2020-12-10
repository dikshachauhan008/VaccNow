package com.xebia.vaccnow.service;

import com.xebia.vaccnow.model.Schedule;

import java.util.concurrent.ExecutionException;

public interface EmailService {
    void sendMail(String emailAddress, Schedule schedule) throws ExecutionException, InterruptedException;
}

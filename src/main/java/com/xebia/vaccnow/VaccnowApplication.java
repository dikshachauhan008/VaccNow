package com.xebia.vaccnow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class VaccnowApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaccnowApplication.class, args);
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newSingleThreadExecutor();
	}

}

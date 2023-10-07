package com.derso.disparotimeouts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DisparotimeoutsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisparotimeoutsApplication.class, args);
	}

}

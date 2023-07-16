package com.derso.architecture.timeout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

// Ref.:
// https://docs.spring.io/spring-framework/reference/integration/scheduling.html

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class TimeoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeoutApplication.class, args);
	}

}

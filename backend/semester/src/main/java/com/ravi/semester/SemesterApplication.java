package com.ravi.semester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync	
public class SemesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SemesterApplication.class, args);
	}

}

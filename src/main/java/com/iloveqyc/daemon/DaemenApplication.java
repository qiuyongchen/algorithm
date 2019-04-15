package com.iloveqyc.daemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DaemenApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaemenApplication.class, args);
	}

}

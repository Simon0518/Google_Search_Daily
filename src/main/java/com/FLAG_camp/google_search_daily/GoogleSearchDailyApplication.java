package com.FLAG_camp.google_search_daily;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GoogleSearchDailyApplication {
	
	public static final Logger logger = LoggerFactory.getLogger(GoogleSearchDailyApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(GoogleSearchDailyApplication.class, args);
	}
}

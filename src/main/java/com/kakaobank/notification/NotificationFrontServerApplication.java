package com.kakaobank.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotificationFrontServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationFrontServerApplication.class, args);
	}

}

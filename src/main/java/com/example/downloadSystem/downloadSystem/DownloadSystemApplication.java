package com.example.downloadSystem.downloadSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DownloadSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DownloadSystemApplication.class, args);
	}

}

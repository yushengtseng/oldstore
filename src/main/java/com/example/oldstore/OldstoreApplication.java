package com.example.oldstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class OldstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OldstoreApplication.class, args);
	}
}

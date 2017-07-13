package com.vodafone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan
public class CustomerbillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerbillingApplication.class, args);
	}
}

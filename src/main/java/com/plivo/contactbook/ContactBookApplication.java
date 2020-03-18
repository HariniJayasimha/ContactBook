package com.plivo.contactbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactBookApplication {

	public static void main(String[] args) {

		System.out.println("Contact Application started");
		SpringApplication.run(ContactBookApplication.class, args);
	}

}

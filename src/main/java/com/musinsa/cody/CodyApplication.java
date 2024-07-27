package com.musinsa.cody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CodyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodyApplication.class, args);
	}

}

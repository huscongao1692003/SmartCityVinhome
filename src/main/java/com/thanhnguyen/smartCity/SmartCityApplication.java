package com.thanhnguyen.smartCity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.thanhnguyen.smartCity.repository")
@EntityScan("com.thanhnguyen.smartCity.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication

public class SmartCityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartCityApplication.class, args);
	}

}

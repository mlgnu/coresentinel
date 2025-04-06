package com.districtcore.coresentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.districtcore.coresentinel.repository")
public class CoreSentinelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreSentinelApplication.class, args);
	}

}

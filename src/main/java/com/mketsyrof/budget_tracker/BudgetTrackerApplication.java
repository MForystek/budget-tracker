package com.mketsyrof.budget_tracker;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BudgetTrackerApplication {

	@Bean
	public OpenAPI swaggerHeader() {
		return new OpenAPI().info(new Info()
				.title("Budget Tracker API")
				.description("API for family Budget Tracker application.")
				.version("0.0.1"));
	}

	public static void main(String[] args) {
		SpringApplication.run(BudgetTrackerApplication.class, args);
	}

}

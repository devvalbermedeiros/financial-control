package com.valber.financial_control;

import org.springframework.boot.SpringApplication;

public class TestFinancialControlApplication {

	public static void main(String[] args) {
		SpringApplication.from(FinancialControlApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

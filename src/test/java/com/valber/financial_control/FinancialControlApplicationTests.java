package com.valber.financial_control;

import com.valber.financial_control.infrastructure.api.controllers.CategoryController;
import com.valber.financial_control.infrastructure.api.controllers.TransactionController;
import com.valber.financial_control.infrastructure.api.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class FinancialControlApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

	@Test
	void categoryControllerShouldBeLoaded() {
		assertThat(applicationContext.getBean(CategoryController.class)).isNotNull();
	}

	@Test
	void transactionControllerShouldBeLoaded() {
		assertThat(applicationContext.getBean(TransactionController.class)).isNotNull();
	}

	@Test
	void userControllerShouldBeLoaded() {
		assertThat(applicationContext.getBean(UserController.class)).isNotNull();
	}

}

package fr.insee.edtmanagement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.insee.edtmanagement.controller.HealcheckController;

@SpringBootTest
class EdtManagementApplicationTests {
	
	@Autowired
	HealcheckController hcController;

	@Test
	void contextLoads() {
		assertThat(hcController).isNotNull();
	}

}
	
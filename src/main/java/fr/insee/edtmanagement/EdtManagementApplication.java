package fr.insee.edtmanagement;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

import fr.insee.edtmanagement.utils.PropertiesLogger;

@SpringBootApplication
@EnableCaching
public class EdtManagementApplication {



	public static void main(String[] args) {
		configureApplicationBuilder(new SpringApplicationBuilder()).build().run(args);
	}

	public static SpringApplicationBuilder configureApplicationBuilder(
			SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder.sources(EdtManagementApplication.class).listeners(new PropertiesLogger());
	}
}

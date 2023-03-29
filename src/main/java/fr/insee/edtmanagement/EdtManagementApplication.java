package fr.insee.edtmanagement;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import fr.insee.edtmanagement.utils.PropertiesLogger;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
		info = @Info(title = "EDT Management API"),
		servers = {@Server(url = "{host}/", description = "Default Server URL")}

		)
@SpringBootApplication
public class EdtManagementApplication {

	public static void main(String[] args) {
		configureApplicationBuilder(new SpringApplicationBuilder()).build().run(args);
	}

	public static SpringApplicationBuilder configureApplicationBuilder(
			SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder.sources(EdtManagementApplication.class).listeners(new PropertiesLogger());
	}
}

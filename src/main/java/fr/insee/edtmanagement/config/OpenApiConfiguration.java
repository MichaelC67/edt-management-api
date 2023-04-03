package fr.insee.edtmanagement.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfiguration {
	
	@Value("${info.app.version:unknown}")
	String appVersion;

	@Bean
	OpenAPI customOpenAPI() {

		Server server = new Server();
		server.setUrl("/");
		server.setDescription("Default URL");

		return new OpenAPI().components(new Components())
				.info(new Info()
						.title("EDT Management API")
						.version(appVersion)
						.description("API giving the list of surveys allocated to the interviewer and the reviewer for the survey \"Emploi du Temps\"")
						)
				.servers(List.of(server));
	}

}

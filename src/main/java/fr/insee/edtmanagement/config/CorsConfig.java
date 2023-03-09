package fr.insee.edtmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
public class CorsConfig {

	@Value("${fr.insee.edtmanagement.corsAllowedOrigins}")
	private String corsAllowedOrigins;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry. addMapping("/**")
						.allowCredentials(true)
				        .allowedOriginPatterns(corsAllowedOrigins)
				        .allowedHeaders("*")
				        .allowedMethods("*")
				        .maxAge(3600L);
			}

		};
	}

}

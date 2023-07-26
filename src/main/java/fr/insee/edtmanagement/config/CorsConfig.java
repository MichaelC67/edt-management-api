package fr.insee.edtmanagement.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration(proxyBeanMethods = false)
public class CorsConfig {

	@Value("${fr.insee.edtmanagement.corsAllowedOrigins}")
	private String corsAllowedOrigins;
	
	@Bean	
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList(corsAllowedOrigins));
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setMaxAge(3600L);
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
		
	}

}

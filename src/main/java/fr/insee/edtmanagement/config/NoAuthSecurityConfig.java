package fr.insee.edtmanagement.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@ConditionalOnExpression("'noauth'.equalsIgnoreCase('${fr.insee.edtmanagement.auth-mode}')")
@ConditionalOnMissingBean(OAuth2SecurityConfig.class)
public class NoAuthSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
		.headers()
		.frameOptions()
		.disable()// for h2 console in noauth mode
		.and()
		.csrf()
		.disable() // required to allow POST method
		.authorizeHttpRequests()
		.anyRequest()
		.permitAll();
		
		log.info("Default authentication activated - no auth ");
		
		return http.build();
	}

}
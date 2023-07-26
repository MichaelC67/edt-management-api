package fr.insee.edtmanagement.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import fr.insee.edtmanagement.constants.Constants;

@Configuration
@EnableWebSecurity
@ConditionalOnExpression("'oauth2'.equalsIgnoreCase('${fr.insee.edtmanagement.auth-mode}')")
@ConditionalOnMissingBean(NoAuthSecurityConfig.class)
public class OAuth2SecurityConfig {
	
	@Value("${fr.insee.edtmanagement.admin-role}")
	private String roleAdmin;
	
	@Value("${fr.insee.edtmanagement.claim.role}")
	private String tokenClaimRole;
	
	
	private static final String[] AUTH_WHITELIST = {
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**",
			"/swagger-ui/**",
			// other public endpoints of your API may be appended to this array
			Constants.API_HEALTHCHECK_URL
	};
	
	private static final String[] AUTH_AUTHENTICATION_REQUIRED = {
			Constants.API_HABILITATION_URL,
			Constants.API_ASSIGMENTS_AUTHENTICATED_REVIEWER,
			Constants.API_ASSIGMENTS_AUTHENTICATED_INTERVIEWER,
			Constants.API_ASSIGMENT_FOR_INTEVIEWERID_URL,
			Constants.API_ASSIGMENT_FOR_REVIEWERID_URL,
	};
	
	private static final String[] AUTH_ADMIN_REQUIRED = {
			Constants.API_UPDATEDB_URL,
			Constants.API_UPDATEDB_WITH_FILE_URL
	};


	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		 	http
		 	.csrf(csrf -> csrf.disable())//API MODE
		 	.cors(withDefaults())
		 	.sessionManagement(sessionsManagment -> sessionsManagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(
						configurer -> 
						configurer
						.requestMatchers(AUTH_WHITELIST).permitAll()
						.requestMatchers(AUTH_AUTHENTICATION_REQUIRED).authenticated()
						.requestMatchers(AUTH_ADMIN_REQUIRED).hasAuthority(roleAdmin)
						.requestMatchers("/*").permitAll()
						.anyRequest().authenticated())
				// Enable JWT Authentication
				.oauth2ResourceServer(
						(oauth2) -> 
							oauth2.jwt( jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
	        

				return http.build();
	}
	
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
	    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
	    grantedAuthoritiesConverter.setAuthoritiesClaimName(tokenClaimRole);
	    //Remove SCOPE_ Prefix added by default 
	    grantedAuthoritiesConverter.setAuthorityPrefix("");
	    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
	    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
	    return jwtAuthenticationConverter;
	}

}

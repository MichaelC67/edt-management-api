package fr.insee.edtmanagement.config;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfiguration {
	
	
    @Value("${fr.insee.edtmanagement.auth-server-url:}")
    private String authServerURL;

    @Value("${info.app.version:}")
    private String appVersion;
    
    @Value("${fr.insee.edtmanagement.auth-server-query-params:}")
    private String queryParamsAuthServer;
    

    private static final String SCHEMEKEYCLOAK = "oAuthScheme";
	
	   @Bean
	    public OpenAPI customOpenApiOAuth() {
	        final OpenAPI openapi = new OpenAPI()
	        		.info(new Info().title("Swagger API REST EDT Managment").version(appVersion));
	        
	        
	        openapi.components(new Components().addSecuritySchemes(SCHEMEKEYCLOAK, new SecurityScheme()
	                .type(SecurityScheme.Type.OAUTH2).in(SecurityScheme.In.HEADER)
	                .description("OAuth Authentication ")
	                .flows(new OAuthFlows().authorizationCode(new OAuthFlow()
	                        .authorizationUrl(authServerURL + "/protocol/openid-connect/auth" + queryParamsAuthServer)
	                        .tokenUrl(authServerURL + "/protocol/openid-connect/token" + queryParamsAuthServer)
	                        .refreshUrl(authServerURL +  "/protocol/openid-connect" + queryParamsAuthServer))
	           )));
        			
	        return openapi;
	    }

  
	    @Bean
	    public OperationCustomizer addToken() {
	        // configuration for the  Swagger to use the token
	        return (operation, handlerMethod) -> operation
	                .addSecurityItem(new SecurityRequirement().addList(SCHEMEKEYCLOAK))
	                ;
	    }
	    
}

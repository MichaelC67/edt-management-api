package fr.insee.edtmanagement.constants;

public class Constants {
	
	private Constants() {
	}
	
	// Role declared by Queen-Back-Office
	public static final String INTERVIEWER = "interviewer";
	public static final String REVIEWER = "reviewer";

	//API Endpoints expected by Queen-Back-Office
	public static final String API_HABILITATION_ENDPOINT = "/api/check-habilitation";
	
	// HealthCheck url
	public static final String API_HEALTH_CHECK = "/api/healthcheck";
	
	//expected Response by Queen-Back-Office for habilitated ressources
	public static final String HABILITATED_RESPONSE_BODY = "habilitated";
	public static final String NO_HABILITATION_RESPONSE_BODY = "";

}

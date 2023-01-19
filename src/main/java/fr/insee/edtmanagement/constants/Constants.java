package fr.insee.edtmanagement.constants;

public class Constants {
	
	private Constants() {
	}
	
	// Role declared by Queen-Back-Office
	public static final String INTERVIEWER = "interviewer";
	public static final String REVIEWER = "reviewer";
	
	//expected Response by Queen-Back-Office for habilitated ressources
	public static final String HABILITATED_RESPONSE_BODY_KEY = "habilitated";
	
	//Endpoints URLs
	//DbController
	public static final String API_UPDATEDB_URL =  "/api/admin/update-db";
	public static final String API_UPDATEDB_WITH_FILE_URL ="/api/admin/update-db-with-file";
	
	//HabilitationController (expected by Queen-Back-Office)
	public static final String API_HABILITATION_URL = "/api/check-habilitation";
	
	//HealthCheckController
	public static final String API_HEALTHCHECK_URL = "/api/healthcheck";
	
	//SurveyAssigment
	public static final String API_ASSIGMENT_FOR_SURVEYID_URL="/api/survey-assigment/survey-unit/{survey-id}";
	public static final String API_ASSIGMENT_FOR_INTEVIEWERID_URL="/api/survey-assigment/interviewer/{interviewer-id}";
	public static final String API_ASSIGMENT_FOR_REVIEWERID_URL="/api/survey-assigment/reviewer/{reviewer-id}";

}

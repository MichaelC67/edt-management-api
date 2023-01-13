package fr.insee.edtmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.insee.edtmanagement.constants.Constants;
import fr.insee.edtmanagement.service.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class HabilitationController {

	@Autowired
	private AuthorizationService authorizationService;

	@Operation(summary = "Check the habilitation for a survey")
	@GetMapping(path = Constants.API_HABILITATION_URL)
	public String authorized(@RequestParam(value = "id", required = true) String surveyId,
			@RequestParam(value = "role", required = false) String expectedRole,
			@RequestParam(value = "campaign", required = true) String campaignId,
			@RequestParam(value = "idep", required = true) String idep) {

		return authorizationService.isAuthorized(surveyId, expectedRole, campaignId, idep) ? 
			 Constants.HABILITATED_RESPONSE_BODY : Constants.NO_HABILITATION_RESPONSE_BODY ;
	}

}
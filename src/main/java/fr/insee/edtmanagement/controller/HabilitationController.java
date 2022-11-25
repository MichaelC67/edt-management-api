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
	AuthorizationService as;

	@Operation(summary = "Check the habilitation for a survey")
	@GetMapping(path = Constants.API_HABILITATION_ENDPOINT)
	public String authorized(@RequestParam(value = "survey-id", required = true) String surveyId,
			@RequestParam(value = "expected-role", required = true) String expectedRole,
			@RequestParam(value = "campaign-id", required = true) String campaignId,
			@RequestParam(value = "idep", required = true) String idep) {

		if (as.isAuthorized(surveyId, expectedRole, campaignId, idep)) {
			return Constants.HABILITATED_RESPONSE_BODY;
		}
		return "";
	}

}
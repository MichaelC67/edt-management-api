package fr.insee.edtmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;

@RestController
public class HealcheckController {
	
	@Autowired
	private SurveyAssigmentRepository surveyAssignmentRepository;
	
	@GetMapping(path = "/api/info")
	public String healthCheck() {			
		Long assigmentNumber= surveyAssignmentRepository.count();
		return "Info : "+assigmentNumber+" lines in database";
	}

}

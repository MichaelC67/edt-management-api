package fr.insee.edtmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;

@RestController
public class HealcheckController {
	
	@Autowired
	SurveyAssigmentRepository sar;
	
	@GetMapping(path = "/api/info")
	public String healthCheck() {
				
		Long assigmentNumber= sar.count();
		StringBuilder sb = new StringBuilder();
		sb.append("Info : ");
		sb.append(assigmentNumber);
		sb.append(" lines in database");
		
		return sb.toString();

	}

}

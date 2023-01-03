package fr.insee.edtmanagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.insee.edtmanagement.constants.Constants;
import fr.insee.edtmanagement.service.SurveyAssigmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DbController {

	@Autowired
	private SurveyAssigmentService surveyAssigmentService;

	@Operation(summary = "Update DB from file defined in properties")
	@GetMapping(path = Constants.API_UPDATEDB_URL)
	public ResponseEntity<String> updateDB() {
		log.info("Launching DB update with DbController");
		return surveyAssigmentService.initDB() ? ResponseEntity.ok("Db updated !") : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() ;
	}
	
	@Operation(summary = "Integrate assignment file")
	@PostMapping(
		    path = Constants.API_UPDATEDB_WITH_FILE_URL,
		   consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> updataDbWithFile(@RequestParam("file") final MultipartFile file) {
		
		boolean kDbUpdated = surveyAssigmentService.populateDB(file.getResource());
		return  kDbUpdated ? ResponseEntity.ok("Db Updated !") : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}

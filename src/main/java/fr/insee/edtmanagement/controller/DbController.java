package fr.insee.edtmanagement.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.insee.edtmanagement.service.SurveyAssigmentService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class DbController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbController.class);

	@Autowired
	SurveyAssigmentService sas;

	@Operation(summary = "Update DB from file defined in properties")
	@GetMapping(path = "/api/admin/update-db")
	public void updateDB() {
		sas.initDB();
	}
	
	@Operation(summary = "Integrate assignment file")
	@PostMapping(
		    path = "/api/admin/update-db-with-file",
		   consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> updataDbWithFile(@RequestParam("file") final MultipartFile file) {
		
		try {
			sas.populateDB(file.getResource());
		} catch (Exception e) {
			LOGGER.error("Updating database with file resulting in 400");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		LOGGER.info("Database updated with {} ",file.getName());
		return  ResponseEntity.status(HttpStatus.OK).build();
	}

}

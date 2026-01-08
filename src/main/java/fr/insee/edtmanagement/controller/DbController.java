package fr.insee.edtmanagement.controller;


import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import fr.insee.edtmanagement.constants.Constants;
import fr.insee.edtmanagement.domain.SurveyAssigment;
import fr.insee.edtmanagement.service.SurveyAssigmentService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DbController {

	@Autowired
	private SurveyAssigmentService surveyAssigmentService;

	@Operation(summary = "Update DB from file defined in properties")
	@Hidden
	@GetMapping(path = Constants.API_UPDATEDB_URL)
	public ResponseEntity<String> updateDB() {
		log.info("Launching DB update with DbController");
		return surveyAssigmentService.initDB() ? ResponseEntity.ok("Db updated !") : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() ;
	}
	
	@Operation(summary = "Export SurveyAssignments as CSV")
	@GetMapping(value = Constants.API_GET_ASSIGNMENTS_IN_DB, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getSurveyAssignments(HttpServletResponse response) throws IOException {

		List<SurveyAssigment> surveys = surveyAssigmentService.getAllSurveysForExport();

		response.setContentType("text/csv;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"survey-assignments.csv\"");

		Writer writer = response.getWriter();
		StatefulBeanToCsv<SurveyAssigment> csvWriter = new StatefulBeanToCsvBuilder<SurveyAssigment>(writer).build();

		try {
			csvWriter.write(surveys);
		} catch (Exception e) {
			throw new RuntimeException("CSV Export failed", e);
		}

		writer.flush();

	}


	
	@Operation(summary = "Integrate assignment file")
	@PostMapping(
		    path = Constants.API_UPDATEDB_WITH_FILE_URL,
		   consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> updataDbWithFile(@RequestParam("file") final MultipartFile file) {
		
		boolean kDbUpdated = surveyAssigmentService.populateFullDB(file.getResource());
		return  kDbUpdated ? ResponseEntity.ok("Db Updated !") : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@Operation(summary = "Integrate assignment file by campaignId")
	@PostMapping(
		    path = Constants.API_UPDATEDB_WITH_FILE_URL_BY_CAMPAIGN,
		   consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> updataDbWithFileByCampaignId(@RequestParam("file") final MultipartFile file,@RequestParam("campaignId") final String campaignId) {
		
		boolean kDbUpdated = surveyAssigmentService.populateDBByCampaignId(file.getResource(),campaignId);
		return  kDbUpdated ? ResponseEntity.ok("Db Updated !") : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	

}

package fr.insee.edtmanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.insee.edtmanagement.constants.Constants;
import fr.insee.edtmanagement.domain.SurveyAssigment;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class SurveyAssigmentController {

	@Autowired
	private SurveyAssigmentRepository surveyAssigmentRepository;

	@Operation(summary = "Get the assigments informations for a survey")
	@GetMapping(path = Constants.API_ASSIGMENT_FOR_SURVEYID_URL)
	public ResponseEntity<SurveyAssigment> assigmentBySurveyID(@PathVariable(value = "survey-id") String surveyID) {
		Optional<SurveyAssigment> surveyAssigment = surveyAssigmentRepository.findBySurveyUnitId(surveyID);
		if (surveyAssigment.isPresent()) {
			return ResponseEntity.ok(surveyAssigment.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Get all the survey assigments for an interviewer")
	@GetMapping(path = Constants.API_ASSIGMENT_FOR_INTEVIEWERID_URL)
	public ResponseEntity<List<SurveyAssigment>> assigmentByHouseholdID(
			@PathVariable(value = "interviewer-id") String interviewerId) {
		Optional<List<SurveyAssigment>> surveyAssigments = surveyAssigmentRepository.findByInterviewerId(interviewerId);
		if (surveyAssigments.isPresent()) {
			return ResponseEntity.ok(surveyAssigments.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Get all the survey assigments for reviewer")
	@GetMapping(path = Constants.API_ASSIGMENT_FOR_REVIEWERID_URL)
	public ResponseEntity<List<SurveyAssigment>> assigmentByReviewerID(
			@PathVariable(value = "reviewer-id") String reviewerId) {
		Optional<List<SurveyAssigment>> surveyAssigments = surveyAssigmentRepository.findByReviewerId(reviewerId);

		if (surveyAssigments.isPresent()) {
			return ResponseEntity.ok(surveyAssigments.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}

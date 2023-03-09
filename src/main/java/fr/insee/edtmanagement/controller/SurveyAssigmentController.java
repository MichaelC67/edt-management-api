package fr.insee.edtmanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.insee.edtmanagement.constants.Constants;
import fr.insee.edtmanagement.domain.SurveyAssigment;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SurveyAssigmentController {

	@Autowired
	private SurveyAssigmentRepository surveyAssigmentRepository;
	
	@Value("${fr.insee.edtmanagement.claim.user-id}")
	private String claimNameForUserId;


	@Operation(summary = "Get the assigments informations for a survey")
	@GetMapping(path = Constants.API_ASSIGMENT_FOR_SURVEYID_URL)
	public ResponseEntity<SurveyAssigment> assigmentBySurveyID(@PathVariable(value = "survey-id") String surveyID) {
		Optional<SurveyAssigment> surveyAssigment = surveyAssigmentRepository.findBySurveyUnitIdIgnoreCase(surveyID);
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
		Optional<List<SurveyAssigment>> surveyAssigments = surveyAssigmentRepository.findByInterviewerIdIgnoreCase(interviewerId);
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
		Optional<List<SurveyAssigment>> surveyAssigments = surveyAssigmentRepository.findByReviewerIdIgnoreCase(reviewerId);

		if (surveyAssigments.isPresent()) {
			return ResponseEntity.ok(surveyAssigments.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Get all the survey assigments for an authenticated reviewer")
	@GetMapping(path = Constants.API_ASSIGMENTS_AUTHENTICATED_REVIEWER)
	public ResponseEntity<List<SurveyAssigment>> myAssigmentsAsAReviewer(@AuthenticationPrincipal Jwt jwt) {
		String userId = jwt.getClaimAsString(claimNameForUserId);
		if (userId != null) {
			log.info("Getting assignments for reviewer with userId {} found in token", userId);
			return ResponseEntity.of(surveyAssigmentRepository.findByReviewerIdIgnoreCase(userId));
		} else {
			log.warn("No userId found in token");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Get all the survey assigments for a authenticated interviewer")
	@GetMapping(path = Constants.API_ASSIGMENTS_AUTHENTICATED_INTERVIEWER)
	public ResponseEntity<List<SurveyAssigment>> myAssigmentsAsAnInterviewer(@AuthenticationPrincipal Jwt jwt) {
		String userId = jwt.getClaimAsString(claimNameForUserId);
		if (userId != null) {
			log.info("Getting assignments for reviewer with userId {} found in token", userId);
			return ResponseEntity.of(surveyAssigmentRepository.findByInterviewerIdIgnoreCase(userId));
		} else {
			log.warn("No userId found in token");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}

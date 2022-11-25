package fr.insee.edtmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.insee.edtmanagement.domain.SurveyAssigment;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class SurveyAssigmentController {

	@Autowired
	SurveyAssigmentRepository sar;

	@Operation(summary = "Get the assigments informations for a survey")
	@GetMapping(path = "/api/survey-assigment/survey-unit/{survey-id}")
	public SurveyAssigment assigmentBySurveyID(@PathVariable(value = "survey-id") String surveyID) {
		return sar.findBySurveyUnitId(surveyID);
	}

	@Operation(summary = "Get all the survey assigments for an interviewer")
	@GetMapping(path = "/api/survey-assigment/interviewer/{interviewer-id}")
	public List<SurveyAssigment> assigmentByHouseholdID(@PathVariable(value = "interviewer-id") String interviewerId) {
		return sar.findByInterviewerId(interviewerId);
	} 

	@Operation(summary = "Get all the survey assigments for reviewer")
	@GetMapping(path = "/api/survey-assigment/reviewer/{reviewer-id}")
	public List<SurveyAssigment> assigmentByReviewerID(@PathVariable(value = "reviewer-id") String reviewerId) {
		return sar.findByReviewerId(reviewerId);
	}

}

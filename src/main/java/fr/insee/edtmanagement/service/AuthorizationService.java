package fr.insee.edtmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.insee.edtmanagement.constants.Constants;
import fr.insee.edtmanagement.domain.SurveyAssigment;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthorizationService {

	@Autowired
	private SurveyAssigmentRepository surveyAssigmentRepository;

	public boolean isAuthorized(String surveyId, String expectedRole, String campaignId, String idep) {

		log.debug("Checking Authorization of idep : {} on surveyId : {} campaign : {} role :  {} ", idep, surveyId,
				campaignId, expectedRole);

		if (Constants.REVIEWER.equals(expectedRole)) {
			SurveyAssigment assigment = surveyAssigmentRepository.findByReviewerIdAndSurveyUnitIdAndCampaignId(idep, surveyId, campaignId);
			if (assigment != null) {
				return true;
			}

			return false;
		}

		// TODO Check if in Queen Back Office interviewer role is omitted
		// if (Constants.INTERVIEWER.equals(expectedRole)) {
		SurveyAssigment assigmentAsInterviewer = surveyAssigmentRepository.findByInterviewerIdAndSurveyUnitIdAndCampaignId(idep, surveyId,
				campaignId);
		if (assigmentAsInterviewer != null) {
			return true;
		}
		return false;
	}

}

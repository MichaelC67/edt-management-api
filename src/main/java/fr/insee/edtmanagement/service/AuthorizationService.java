package fr.insee.edtmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.insee.edtmanagement.constants.Constants;
import fr.insee.edtmanagement.domain.SurveyAssigment;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;

@Service
public class AuthorizationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);

	@Autowired
	public SurveyAssigmentRepository sar;

	public boolean isAuthorized(String surveyId, String expectedRole, String campaignId, String idep) {

		LOGGER.debug("Checking Authorization of idep : {} on surveyId : {} campaign : {} role :  {} ", idep, surveyId,
				campaignId, expectedRole);

		if (Constants.REVIEWER.equals(expectedRole)) {
			SurveyAssigment assigment = sar.findByReviewerIdAndSurveyUnitIdAndCampaignId(idep, surveyId, campaignId);
			if (assigment != null) {
				return true;
			}

			return false;
		}

		// TODO Check if in Queen Back Office interviewer role is omitted
		// if (Constants.INTERVIEWER.equals(expectedRole)) {
		SurveyAssigment assigmentAsInterviewer = sar.findByInterviewerIdAndSurveyUnitIdAndCampaignId(idep, surveyId,
				campaignId);
		if (assigmentAsInterviewer != null) {
			return true;
		}
		return false;
	}

}

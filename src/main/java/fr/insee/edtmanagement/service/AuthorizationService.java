package fr.insee.edtmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.insee.edtmanagement.constants.Constants;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthorizationService {

	@Autowired
	private SurveyAssigmentRepository surveyAssigmentRepository;

	public Boolean isAuthorized(String surveyId, String expectedRole, String campaignId, String idep) {

		log.debug("Checking Authorization of idep : {} on surveyId : {} campaign : {} role :  {} ", idep, surveyId,
				campaignId, expectedRole);

		if(Constants.REVIEWER.equals(expectedRole)) {
			return checkReviewerHabilitation(surveyId, campaignId, idep);
		}
		
		// Queen Back Office  doesn t send role when it is an interviewer
		 return checkInterviewerHabilitation(surveyId, campaignId, idep);
		
	}

	private boolean checkInterviewerHabilitation(String surveyId, String campaignId, String idep) {
		return surveyAssigmentRepository
				.findByInterviewerIdAndSurveyUnitIdAndCampaignId(idep, surveyId, campaignId).isPresent();
	}

	private boolean checkReviewerHabilitation(String surveyId, String campaignId, String idep) {
		return surveyAssigmentRepository
				.findByReviewerIdAndSurveyUnitIdAndCampaignId(idep, surveyId, campaignId).isPresent();
	}
}

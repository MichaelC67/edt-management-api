package fr.insee.edtmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import fr.insee.edtmanagement.constants.Constants;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthorizationService {

	@Autowired
	private SurveyAssigmentRepository surveyAssigmentRepository;

	@Cacheable("isAuthorized")
	public Boolean isAuthorized(String surveyId, String expectedRole, String campaignId, String userId) {

		log.debug("Service checking Authorization of userId : {} on surveyId : {} campaign : {} role :  {} ", userId, surveyId,
				campaignId, expectedRole);

		if(Constants.REVIEWER.equals(expectedRole)) {
			return checkReviewerHabilitation(surveyId, campaignId, userId);
		}
		
		// Queen Back Office  doesn t send role when it is an interviewer
		 return checkInterviewerHabilitation(surveyId, campaignId, userId);
		
	}

	private boolean checkInterviewerHabilitation(String surveyId, String campaignId, String userId) {
		return surveyAssigmentRepository
				.findByInterviewerIdIgnoreCaseAndSurveyUnitIdAndCampaignId(userId, surveyId, campaignId).isPresent();
	}

	private boolean checkReviewerHabilitation(String surveyId, String campaignId, String userId) {
		return surveyAssigmentRepository
				.findByReviewerIdIgnoreCaseAndSurveyUnitIdAndCampaignId(userId, surveyId, campaignId).isPresent();          
	}
}

package fr.insee.edtmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${fr.insee.edtmanagement.loose-check-habilitation}")
	private boolean looseCheckHabilitation;

	@Cacheable("isAuthorized")
	public Boolean isAuthorized(String surveyId, String expectedRole, String campaignId, String userId) {

		log.debug("Service checking Authorization of userId : {} on surveyId : {} campaign : {} role :  {} ", userId, surveyId,
				campaignId, expectedRole);
		
		if (Constants.INTERVIEWER.equals(expectedRole)) {
			if (checkInterviewerHabilitation(surveyId, campaignId, userId)) {
				return true;
				// Workaround to handle checkHabilitation request from queen BO in case of 
				// some PUT request made by a reviewer 
			} else if (looseCheckHabilitation) {
				log.info("Looking for an habilitation for user {} as a reviewer",userId);
				return checkReviewerHabilitation(surveyId, campaignId, userId);
			}
		}

		if (Constants.REVIEWER.equals(expectedRole)) {
			return checkReviewerHabilitation(surveyId, campaignId, userId);
		}

		return false;

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

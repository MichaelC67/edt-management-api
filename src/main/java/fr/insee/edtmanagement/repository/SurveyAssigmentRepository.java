package fr.insee.edtmanagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.insee.edtmanagement.domain.SurveyAssigment;

public interface SurveyAssigmentRepository extends CrudRepository<SurveyAssigment, Long> {

	List<SurveyAssigment> findByInterviewerId(String interviewerId);
	List<SurveyAssigment> findByReviewerId(String reviewerID);
	SurveyAssigment findBySurveyUnitId(String surveyUnitId);

	SurveyAssigment findByInterviewerIdAndSurveyUnitIdAndCampaignId(String interviewerId, String surveyUnitId,
			String campaignId);

	SurveyAssigment findByReviewerIdAndSurveyUnitIdAndCampaignId(String reviewerId, String surveyUnitId,
			String campaignId);

}

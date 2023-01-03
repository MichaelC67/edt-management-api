package fr.insee.edtmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import fr.insee.edtmanagement.domain.SurveyAssigment;

public interface SurveyAssigmentRepository extends CrudRepository<SurveyAssigment, Long> {

	Optional<List<SurveyAssigment>> findByInterviewerId(String interviewerId);
	Optional<List<SurveyAssigment>> findByReviewerId(String reviewerID);
	Optional<SurveyAssigment> findBySurveyUnitId(String surveyUnitId);

	Optional<SurveyAssigment> findByInterviewerIdAndSurveyUnitIdAndCampaignId(String interviewerId, String surveyUnitId,
			String campaignId);

	Optional<SurveyAssigment> findByReviewerIdAndSurveyUnitIdAndCampaignId(String reviewerId, String surveyUnitId,
			String campaignId);

}

package fr.insee.edtmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SurveyAssigment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String interviewerId;
	private String surveyUnitId;
	private String reviewerId;
	private String campaignId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInterviewerId() {
		return interviewerId;
	}

	public void setInterviewerId(String interviewerId) {
		this.interviewerId = interviewerId;
	}

	public String getSurveyUnitId() {
		return surveyUnitId;
	}

	public void setSurveyUnitId(String surveyUnitId) {
		this.surveyUnitId = surveyUnitId;
	}

	public String getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

}
package fr.insee.edtmanagement.domain;

import com.opencsv.bean.CsvBindByPosition;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @EqualsAndHashCode @ToString
public class SurveyAssigment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @CsvBindByPosition(position = 0)
	private String interviewerId;
    
    @CsvBindByPosition(position = 1)
	private String surveyUnitId;
    
    @CsvBindByPosition(position = 2)
	private String reviewerId;
    
    @CsvBindByPosition(position = 3)
	private String campaignId;
	
	
}
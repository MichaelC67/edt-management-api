package fr.insee.edtmanagement.domain;

import com.opencsv.bean.CsvBindByPosition;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
	private String interviewerId;
    
    @CsvBindByPosition(position = 1)
    @NotNull
	private String surveyUnitId;
    
    @CsvBindByPosition(position = 2)
    @NotNull
	private String reviewerId;
    
    @CsvBindByPosition(position = 3)
    @NotNull
	private String campaignId;
    
    @CsvBindByPosition(position = 4)
    @NotNull
	private String questionnaireModelId;
    
    @CsvBindByPosition(position = 5)
    @NotNull
	private String interrogationId;
	
}
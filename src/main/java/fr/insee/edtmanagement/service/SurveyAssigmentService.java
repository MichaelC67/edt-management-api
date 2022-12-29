package fr.insee.edtmanagement.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import fr.insee.edtmanagement.domain.SurveyAssigment;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SurveyAssigmentService {

	@Value("${fr.insee.edtmanagement.CsvDataSource:classpath:data/sample.csv}")
	private Resource initialResource;
	
	@Value("${fr.insee.edtmanagement.CsvSeparator:,}")
	private char csvSeparator;
	
	@Value("${fr.insee.edtmanagement.CsvSkipLines:0}")
	private int csvSkipLines;

	@Autowired
	private SurveyAssigmentRepository surveyAssigmentRepository;

	@PostConstruct
	public void initDB() {
		log.info("Init  DB from {} ", initialResource.getFilename());
		populateDB(initialResource);
	}

	public void populateDB(Resource resource) {

		log.info("Start populating the DB from {} ", resource.getFilename());
		
		cleanDB();

		try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {

			CsvToBean<SurveyAssigment> csvToBean = 
					new CsvToBeanBuilder<SurveyAssigment>(reader)
					.withSeparator(csvSeparator)
					.withSkipLines(csvSkipLines)					
					.withType(SurveyAssigment.class)
					.build();

			List<SurveyAssigment> lstSurveyAssigments = csvToBean.parse();

			surveyAssigmentRepository.saveAll(lstSurveyAssigments);

			log.info("{} survey assignments saved in DB", surveyAssigmentRepository.count());

		} catch (Exception e) {
			log.error("An error occured during the load of the DB with the resource"+resource.getFilename(),e );
		}
	}

	public void cleanDB() {
		surveyAssigmentRepository.deleteAll();
		log.info("DB cleared !");
	}

}

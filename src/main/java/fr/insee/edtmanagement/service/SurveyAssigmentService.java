package fr.insee.edtmanagement.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import fr.insee.edtmanagement.domain.SurveyAssigment;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;

@Service
public class SurveyAssigmentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyAssigmentService.class);

	@Value("${fr.insee.edtmanagment.CSVDataSource:classpath:data/sample.csv}")
	Resource resourceFile;

	@Autowired
	public SurveyAssigmentRepository sar;

	public void initDB() {
		LOGGER.info("Init  DB from {} ", resourceFile.getFilename());
		populateDB(resourceFile);
	}

	public void populateDB(Resource resourceFile) {

		LOGGER.info("Start populating the DB from {} ", resourceFile.getFilename());

		List<SurveyAssigment> lstSurveyAssignement = new ArrayList<>();
		
		cleanDB();

		if (!resourceFile.isFile()) {
			try (Reader reader = new InputStreamReader(resourceFile.getInputStream());) {
				lstSurveyAssignement=extractData(reader);
			} catch (IOException e) {
				LOGGER.error("Problem with {}, database may be empty", resourceFile);
			}
		} else {
			try {
				FileReader fileReader = new FileReader(resourceFile.getFile());
				lstSurveyAssignement=extractData(fileReader);
			} catch (IOException e) {
				LOGGER.error("Problem with {}, database may be empty", resourceFile);
			}

		}
		
		sar.saveAll(lstSurveyAssignement);
		
		LOGGER.info("{} survey assignments saved in DB",sar.count());

	}

	public void cleanDB() {
		LOGGER.info("DB cleared !");
		sar.deleteAll();
	}

	private List<SurveyAssigment> extractData(Reader reader) {

		List<SurveyAssigment> lstSurveyAssigment = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(reader);) {

			String line = "";

			while ((line = br.readLine()) != null) {

				String[] data = line.split(",");
				SurveyAssigment surveyAssigment = new SurveyAssigment();
				surveyAssigment.setInterviewerId(data[0]);
				surveyAssigment.setSurveyUnitId(data[1]);
				surveyAssigment.setReviewerId(data[2]);
				surveyAssigment.setCampaignId(data[3]);
				lstSurveyAssigment.add(surveyAssigment);
			}

			LOGGER.info("{} survey assigments extracted ", lstSurveyAssigment.size());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return lstSurveyAssigment;
	}

}

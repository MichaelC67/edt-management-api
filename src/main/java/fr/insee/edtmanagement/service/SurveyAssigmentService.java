package fr.insee.edtmanagement.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

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

	public void populateDB() {

		LOGGER.info("Start populating the DB from {} ", resourceFile.getFilename());

		if (!resourceFile.isFile()) {
			try (Reader reader = new InputStreamReader(resourceFile.getInputStream());) {
				extractData(reader);
			} catch (IOException e) {
				LOGGER.error("Problem with {}, database may be empty", resourceFile);
			}
		} else {
			try {
				FileReader fileReader = new FileReader(resourceFile.getFile());
				extractData(fileReader);
			} catch (IOException e) {
				LOGGER.error("Problem with {}, database may be empty", resourceFile);
			}
		}

	}

	private void extractData(Reader reader) {
		try (BufferedReader br = new BufferedReader(reader);) {

			String line = "";

			while ((line = br.readLine()) != null) {

				String[] data = line.split(",");
				SurveyAssigment surveyAssigment = new SurveyAssigment();
				surveyAssigment.setInterviewerId(data[0]);
				surveyAssigment.setSurveyUnitId(data[1]);
				surveyAssigment.setReviewerId(data[2]);
				surveyAssigment.setCampaignId(data[3]);
				sar.save(surveyAssigment);
			}

			LOGGER.info("DB populated with {} lines", sar.count());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package fr.insee.edtmanagement.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import fr.insee.edtmanagement.domain.SurveyAssigment;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;
import jakarta.validation.constraints.NotNull;
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

	public boolean initDB() {
		log.info("Init  DB from {} ", initialResource.getFilename());
		return populateFullDB(initialResource);
	}

	// TODO throw exception if failure
	
	@CacheEvict(value = "isAuthorized", allEntries = true)
	public boolean populateFullDB(@NotNull Resource resource) {

		boolean kDBPopulated = false;
		try {

			log.info("Start populating the DB from {} ", resource.getFilename());

			List<SurveyAssigment> lstSurveyAssigments = mapCsvToSurveyAssigmentList(resource);

			log.info("{} survey assignments found in {} ", lstSurveyAssigments.size(), resource.getFilename());

			cleanFullDB();

			surveyAssigmentRepository.saveAll(lstSurveyAssigments);

			log.info("{} survey assignments saved in DB", surveyAssigmentRepository.count());
			
			kDBPopulated = true;

		} catch (Exception e) {
			log.error("An error occured during the load of the DB with the resource" + resource.getFilename(), e);
		
		}

		return kDBPopulated;

	}
	
	@CacheEvict(value = "isAuthorized", allEntries = true)
	public boolean populateDBByCampaignId(@NotNull Resource resource,String campaignId) {

		boolean kDBPopulated = false;
		try {

			log.info("Start populating the DB from {} ", resource.getFilename());

			List<SurveyAssigment> lstSurveyAssigments = mapCsvToSurveyAssigmentList(resource);

			log.info("{} survey assignments found in {} ", lstSurveyAssigments.size(), resource.getFilename());
			
			checkAllSurveyAssigmentApplyToCampaignId(lstSurveyAssigments,campaignId);

			cleanDBByCampaignId(campaignId);

			surveyAssigmentRepository.saveAll(lstSurveyAssigments);

			log.info("{} survey assignments saved in DB for campaign {}", surveyAssigmentRepository.count(),campaignId);
			
			kDBPopulated = true;

		} catch (Exception e) {
			log.error("An error occured during the load of the DB with the resource" + resource.getFilename(), e);
		
		}

		return kDBPopulated;

	}

    public List<SurveyAssigment> getAllSurveysForExport() {
        return (List<SurveyAssigment>) surveyAssigmentRepository.findAll();
    }


	private void checkAllSurveyAssigmentApplyToCampaignId(List<SurveyAssigment> lstSurveyAssigments,
			String campaignId) throws Exception{
		
		for (Iterator iterator = lstSurveyAssigments.iterator(); iterator.hasNext();) {
			SurveyAssigment surveyAssigment = (SurveyAssigment) iterator.next();
			
			if(!surveyAssigment.getCampaignId().equals(campaignId)) {
				throw new Exception("This assignement "+surveyAssigment.toString()+" doesn t concern the campaign "+campaignId);
			}
			
		}
		
	}

	/**
	 *  Transform CSV file content into List of SurveyAssigments **/
	private List<SurveyAssigment> mapCsvToSurveyAssigmentList(Resource resource) {
		
		List<SurveyAssigment> lstSurveyAssigments= new ArrayList<SurveyAssigment>();
		
		try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {

			CsvToBean<SurveyAssigment> csvToBean = 
					new CsvToBeanBuilder<SurveyAssigment>(reader)
					.withSeparator(csvSeparator)
					.withSkipLines(csvSkipLines)					
					.withType(SurveyAssigment.class)
					.build();

			lstSurveyAssigments = csvToBean.parse();
			
			return lstSurveyAssigments;
		
			
		} catch (Exception e) {
			log.error("An error occured during the load of the DB with the resource"+resource.getFilename(),e );
		}
		
		return lstSurveyAssigments;
	}

	public void cleanFullDB() {
		surveyAssigmentRepository.deleteAll();
		log.info("DB cleared !");
	}
	
	@Transactional
	public void cleanDBByCampaignId(String campaignId) {
		int NbSurveyAssigmentDeleted=surveyAssigmentRepository.deleteByCampaignId(campaignId);
		log.info(NbSurveyAssigmentDeleted +" removed from DB cleared for campaignId !");
	}



}

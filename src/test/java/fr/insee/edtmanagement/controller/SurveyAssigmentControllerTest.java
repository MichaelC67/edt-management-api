package fr.insee.edtmanagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBean;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import fr.insee.edtmanagement.domain.SurveyAssigment;
import fr.insee.edtmanagement.repository.SurveyAssigmentRepository;
import fr.insee.edtmanagement.service.SurveyAssigmentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SurveyAssigmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SurveyAssigmentService surveyAssigmentService;

    @Autowired
    private SurveyAssigmentRepository surveyAssigmentRepository;

 @BeforeAll
void setup() throws Exception {
    Resource resource = new ClassPathResource("data/sample.csv");
    if (!resource.exists()) {
        throw new IllegalStateException("Fichier data/sample.csv introuvable sur classpath (src/main/resources/data/)");
    }
    
    try (Reader reader = new InputStreamReader(resource.getInputStream())) {
        CsvToBean<SurveyAssigment> csvToBean = new CsvToBeanBuilder<SurveyAssigment>(reader)
                .withType(SurveyAssigment.class)
                .withSeparator(',')
                .withSkipLines(1)
                .build();

        var data = csvToBean.parse();
        surveyAssigmentRepository.deleteAll();
        surveyAssigmentRepository.saveAll(data);

        assertThat(surveyAssigmentRepository.count()).isEqualTo(50);
    }
}

    @Test
    void shouldLoadCsvAndServeSurveyAssignmentEndpoints() throws Exception {
        // interrogation endpoint
        mockMvc.perform(get("/api/survey-assigment/interrogation/0000198a-36e9-762a-351a-690003a51bf3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.interrogationId").value("0000198a-36e9-762a-351a-690003a51bf3"))
                .andExpect(jsonPath("$.interviewerId").value("repondant1"));

        // interviewer endpoint: in sample.csv, repondant1 has 5 assignments
        mockMvc.perform(get("/api/survey-assigment/interviewer/repondant1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));

        // reviewer endpoint: reviewer1 has 5 assignments
        mockMvc.perform(get("/api/survey-assigment/reviewer/reviewer1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));

        // not found behavior
        mockMvc.perform(get("/api/survey-assigment/interrogation/does-not-exist"))
                .andExpect(status().isNotFound());
    }
}

package com.cgm.app.controller;

import com.cgm.app.controller.PatientController;
import com.cgm.app.dto.PatientDto;
import com.cgm.app.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(PatientController.class)
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    private Page<PatientDto> samplePatients;
    private PatientDto samplePatientDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Sample data
        samplePatientDto = new PatientDto(1L, "Tom", "Cat", LocalDate.of(1994, 10,27), "123-45-6789", new ArrayList<>());
        samplePatients = new PageImpl<>(List.of(samplePatientDto));
    }

    @Test
    public void testCreatePatient() throws Exception {
        when(patientService.savePatient(samplePatientDto)).thenReturn(samplePatientDto);
        String requestJson = objectMapper.writeValueAsString(samplePatientDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(requestJson));
    }

    @Test
    public void testGetAllPatients() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        when(patientService.getAllPatients(pageable)).thenReturn(samplePatients);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/patients")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,asc"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void testGetPatient() throws Exception {
        Long patientId = 1L;
        when(patientService.findByPatientId(patientId)).thenReturn(samplePatientDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/patients/{patientId}", patientId));

        resultActions.andExpect(status().isOk());
    }
}

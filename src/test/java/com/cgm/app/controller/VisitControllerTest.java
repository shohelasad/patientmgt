package com.cgm.app;

import com.cgm.app.controller.VisitController;
import com.cgm.app.dto.VisitDto;
import com.cgm.app.enums.VisitReason;
import com.cgm.app.enums.VisitType;
import com.cgm.app.service.PatientService;
import com.cgm.app.service.VisitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@WebMvcTest(VisitController.class)
public class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VisitService visitService;

    @MockBean
    private PatientService patientService;

    private VisitDto visitDto;

    @BeforeEach
    public void setup() {
        visitDto = new VisitDto(1L, LocalDateTime.now(), VisitReason.FIRST_VISIT, VisitType.DOCTOR_OFFICE, "No family history",  2L);
    }

    @Test
    public void testCreateVisit() throws Exception {
        when(visitService.saveVisit(visitDto)).thenReturn(visitDto);
        String requestJson = objectMapper.writeValueAsString(visitDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(requestJson));
    }

    @Test
    public void testGetVisit() throws Exception {
        Long visitId = 1L;
        when(visitService.findByVisitId(visitId)).thenReturn(visitDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/visits/{visitId}", visitId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(visitDto)));
    }

    @Test
    public void testUpdateVisit() throws Exception {
        Long visitId = 1L;
        when(visitService.updateVisit(1L, visitDto)).thenReturn(visitDto);
        String requestJson = objectMapper.writeValueAsString(visitDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/visits/{visitId}", visitId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestJson));
    }
}

package com.cgm.app.service;

import com.cgm.app.dto.VisitDto;
import com.cgm.app.entity.Patient;
import com.cgm.app.entity.Visit;
import com.cgm.app.enums.VisitReason;
import com.cgm.app.enums.VisitType;
import com.cgm.app.exception.ResourceNotFoundException;
import com.cgm.app.repository.PatientRepository;
import com.cgm.app.repository.VisitRepository;
import com.cgm.app.service.PatientService;
import com.cgm.app.service.VisitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class VisitServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitService visitService;

    @InjectMocks
    private PatientService patientService;

    private VisitDto visitDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        visitDto = new VisitDto(1L, LocalDateTime.now(), VisitReason.URGENT_VISIT, VisitType.HOME, "None",  2L);
    }

    @Test
    public void testSaveVisit() {
        Patient patient = new Patient();
        patient.setId(1L);

        when(patientRepository.findById(visitDto.patientId())).thenReturn(Optional.of(patient));
        Visit savedVisit = createSampleVisit();
        when(visitRepository.save(any(Visit.class))).thenReturn(savedVisit);
        VisitDto savedVisitDto = visitService.saveVisit(visitDto);

        assertEquals(1L, savedVisitDto.id().longValue());
        assertEquals(1L, savedVisitDto.patientId().longValue());
        assertEquals(VisitReason.URGENT_VISIT, savedVisitDto.visitReason());
        assertEquals(VisitType.HOME, savedVisitDto.visitType());
        assertEquals("None", savedVisitDto.familyHistory());
    }

    @Test
    public void testUpdateVisit() {
        long visitId = 1L;
        Visit existingVisit = createSampleVisit();

        when(visitRepository.findById(visitId)).thenReturn(java.util.Optional.of(existingVisit));
        when(visitRepository.save(any(Visit.class))).thenReturn(existingVisit);

        VisitDto updatedVisitDto = visitService.updateVisit(visitId, visitDto);

        assertEquals(1L, updatedVisitDto.id().longValue());
        assertEquals(1L, updatedVisitDto.patientId().longValue());
        assertEquals(VisitReason.URGENT_VISIT, updatedVisitDto.visitReason());
        assertEquals(VisitType.HOME, updatedVisitDto.visitType());
        assertEquals("None", updatedVisitDto.familyHistory());
    }

    @Test
    public void testFindByVisitId() {
        long visitId = 1L;
        Visit visit = createSampleVisit();

        when(visitRepository.findById(visitId)).thenReturn(java.util.Optional.of(visit));

        VisitDto foundVisitDto = visitService.findByVisitId(visitId);

        assertEquals(visit.getDateTime(), foundVisitDto.dateTime());
        Assertions.assertEquals(visit.getVisitReason(), foundVisitDto.visitReason());
        assertEquals(visit.getVisitType(), foundVisitDto.visitType());
        assertEquals(visit.getFamilyHistory(), foundVisitDto.familyHistory());
    }

    @Test
    public void testSaveVisitWithNonExistentPatient() {
        when(patientRepository.findById(visitDto.patientId())).thenReturn(java.util.Optional.empty());

        try {
            visitService.saveVisit(visitDto);
        } catch (ResourceNotFoundException e) {
            assertEquals("Patient not found with id: " + visitDto.patientId(), e.getMessage());
        }
    }

    @Test
    public void testUpdateVisitWithNonExistentVisit() {
        long visitId = 1L;

        when(visitRepository.findById(visitId)).thenReturn(java.util.Optional.empty());

        try {
            visitService.updateVisit(visitId, visitDto);
        } catch (ResourceNotFoundException e) {
            assertEquals("Visit not found with id: " + visitId, e.getMessage());
        }
    }

    @Test
    public void testFindByNonExistentVisitId() {
        long visitId = 1L;

        when(visitRepository.findById(visitId)).thenReturn(java.util.Optional.empty());

        try {
            visitService.findByVisitId(visitId);
        } catch (ResourceNotFoundException e) {
            assertEquals("Visit not found with id: " + visitId, e.getMessage());
        }
    }

    private Visit createSampleVisit() {
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setPatient(createSamplePatient());
        visit.setVisitReason(VisitReason.URGENT_VISIT);
        visit.setVisitType(VisitType.HOME);
        visit.setFamilyHistory("None");
        return visit;
    }

    private Patient createSamplePatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        return patient;
    }
}

package com.cgm.app.repository;

import com.cgm.app.dto.VisitDto;
import com.cgm.app.entity.Patient;
import com.cgm.app.entity.Visit;
import com.cgm.app.enums.VisitReason;
import com.cgm.app.enums.VisitType;
import com.cgm.app.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class VisitRepositoryTest {

    @InjectMocks
    private VisitService visitService;

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private PatientRepository patientRepository;

    private VisitDto visitDto;
    private Visit visit;
    private Visit savedVisit;
    Patient savedPatient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        LocalDateTime dateTime = LocalDateTime.now();
        savedPatient = new Patient(1L, "Tom", "Cat", LocalDate.of(1994, 10,27), "123-45-6789", new ArrayList<>());
        visitDto = new VisitDto(1L, dateTime, VisitReason.URGENT_VISIT, VisitType.HOME, "None",  1L);
        visit = new Visit(null, dateTime, VisitReason.URGENT_VISIT, VisitType.HOME, "None",  savedPatient);
        savedVisit = new Visit(1L, dateTime, VisitReason.URGENT_VISIT, VisitType.HOME, "None", savedPatient);
    }

    @Test
    public void testSaveVisit() {
        when(visitRepository.save(any(Visit.class))).thenReturn(savedVisit);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(savedPatient));
        VisitDto savedVisitDto = visitService.saveVisit(visitDto);

        verify(visitRepository, times(1)).save(visit);

        assertNotNull(savedVisitDto);
        assertEquals(savedVisitDto.dateTime(), savedVisit.getDateTime());
        assertEquals(savedVisitDto.visitReason(), savedVisit.getVisitReason());
        assertEquals(savedVisitDto.visitType(), savedVisit.getVisitType());
    }

    @Test
    public void testFindById() {
        visit.setId(1L);
        when(visitRepository.findById(1L)).thenReturn(Optional.of(savedVisit));

        Optional<Visit> foundVisit = visitRepository.findById(1L);

        assertTrue(foundVisit.isPresent());
        assertEquals(1L, foundVisit.get().getId());
        assertEquals(savedVisit.getDateTime(), foundVisit.get().getDateTime());
        assertEquals(savedVisit.getVisitReason(), foundVisit.get().getVisitReason());
        assertEquals(savedVisit.getVisitType(), foundVisit.get().getVisitType());
    }

    @Test
    public void testFindByIdNotPresent() {
        when(visitRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Visit> notFoundVisit = visitRepository.findById(2L);

        assertTrue(notFoundVisit.isEmpty());
    }
}

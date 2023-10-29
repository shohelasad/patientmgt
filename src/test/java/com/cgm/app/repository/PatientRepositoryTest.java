package com.cgm.app.repository;

import com.cgm.app.dto.PatientDto;
import com.cgm.app.entity.Patient;
import com.cgm.app.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class PatientRepositoryTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    private PatientDto patientDto;
    private Patient patient;
    private Patient savedPatient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        patientDto = new PatientDto(1L, "Tom", "Cat", LocalDate.of(1994, 10,27), "123-45-6789", new ArrayList<>());
        patient = new Patient(null, patientDto.name(), patientDto.surname(), patientDto.dateOfBirth(), patientDto.socialSecurityNumber(), new ArrayList<>());
        savedPatient = new Patient(patientDto.id(), patientDto.name(), patientDto.surname(), patientDto.dateOfBirth(), patientDto.socialSecurityNumber(), new ArrayList<>());
    }

    @Test
    public void testSavePatient() {
        when(patientRepository.save(patient)).thenReturn(savedPatient);
        PatientDto savedPatientDto = patientService.savePatient(patientDto);
        assertEquals(patientDto.id(), savedPatientDto.id());

        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void testFindPatientById() {
        Long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(savedPatient));

        PatientDto foundPatientDto = patientService.findByPatientId(patientId);
        assertEquals(patientDto.id(), foundPatientDto.id());

        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    public void testFindAllPatients() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "id"));
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        Page<Patient> page = new PageImpl<>(patients, pageable, patients.size());

        when(patientRepository.findAll(pageable)).thenReturn(page);

        Page<PatientDto> foundPatients = patientService.getAllPatients(pageable);
        assertFalse(foundPatients.isEmpty());
        assertEquals(patients.get(0).getId(), foundPatients.getContent().get(0).id());

        verify(patientRepository, times(1)).findAll(pageable);
    }
}

package com.cgm.app;

import com.cgm.app.dto.PatientDto;
import com.cgm.app.entity.Patient;
import com.cgm.app.exception.ResourceNotFoundException;
import com.cgm.app.repository.PatientRepository;
import com.cgm.app.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private PatientDto samplePatientDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        samplePatientDto = new PatientDto(1L, "Tom", "Cat", LocalDate.of(1994, 10,27), "123-45-6789", new ArrayList<>());
    }

    @Test
    public void testSavePatient() {
        Patient savedPatient = new Patient();
        savedPatient.setId(1L);
        savedPatient.setName("Tom");
        savedPatient.setSurname("Cat");
        savedPatient.setDateOfBirth(LocalDate.of(1994, 10,27));
        savedPatient.setSocialSecurityNumber("123-45-6789");

        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(savedPatient);
        PatientDto result = patientService.savePatient(samplePatientDto);

        assertEquals(savedPatient.getId(), result.id());
        assertEquals(savedPatient.getName(), result.name());
        assertEquals(savedPatient.getSurname(), result.surname());
        assertEquals(savedPatient.getDateOfBirth(), result.dateOfBirth());
        assertEquals(savedPatient.getSocialSecurityNumber(), result.socialSecurityNumber());
    }

    @Test
    public void testGetAllPatients() {
        List<Patient> patients = new ArrayList<>();
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Tom");
        patients.add(patient);

        Page<Patient> patientPage = new PageImpl<>(patients);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        when(patientRepository.findAll(Mockito.any(Pageable.class))).thenReturn(patientPage);
        Page<PatientDto> result = patientService.getAllPatients(pageable);

        assertEquals(patients.size(), result.getTotalElements());
    }

    @Test
    public void testFindByPatientId() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setName("Alice");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        PatientDto result = patientService.findByPatientId(patientId);

        assertEquals(patient.getId(), result.id());
        assertEquals(patient.getName(), result.name());
    }

    @Test
    public void testFindByPatientIdNotFound() {
        Long patientId = 1L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.findByPatientId(patientId));
    }
}

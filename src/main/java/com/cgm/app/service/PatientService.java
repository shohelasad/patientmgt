package com.cgm.app.service;

import com.cgm.app.entity.Patient;
import com.cgm.app.repository.PatientRepository;
import com.cgm.app.Utils.DtoUtil;
import com.cgm.app.dto.PatientDto;
import com.cgm.app.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientDto savePatient(PatientDto patientDto) {
        Patient patient = new Patient();
        mapToPatient(patientDto, patient);
        Patient savedPatient = patientRepository.save(patient);
        log.info("Created a new patient with: {}", savedPatient);
        return DtoUtil.convertToPatientDto(savedPatient);
    }

    public Page<PatientDto> getAllPatients(Pageable pageable) {
        Page<Patient> page = patientRepository.findAll(pageable);
        log.info("Fetching a page: {}", page);
        return page.map(DtoUtil::convertToPatientDto);
    }

    public PatientDto findByPatientId(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        log.info("Fetching a patient with patient id: {}", patientId);
        return DtoUtil.convertToPatientDto(patient);
    }

    private void mapToPatient(PatientDto patientDto, Patient patient) {
        patient.setName(patientDto.name());
        patient.setSurname(patientDto.surname());
        patient.setDateOfBirth(patientDto.dateOfBirth());
        patient.setSocialSecurityNumber(patientDto.socialSecurityNumber());
        patient.setVisits(new ArrayList<>()); //for new patient, visit list is empty
    }
}


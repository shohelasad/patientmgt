package com.cgm.app.controller;

import com.cgm.app.dto.PatientDto;
import com.cgm.app.service.PatientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/patients")
@Validated
public class PatientController {
    private final PatientService patientService;
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Valid  @RequestBody PatientDto patient) {
        log.info("Creating a new patient with: {}", patient);
        PatientDto savedPatientDto = patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatientDto);
    }

    @GetMapping
    public ResponseEntity<Page<PatientDto>> getAllPatients(Pageable pageable) {
        log.info("Fetching all patients as page list : {}" + pageable);
        return ResponseEntity.ok(patientService.getAllPatients(pageable));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable Long patientId) {
        log.info("Fetching a patient with: " + patientId);
        return ResponseEntity.ok(patientService.findByPatientId(patientId));
    }
}

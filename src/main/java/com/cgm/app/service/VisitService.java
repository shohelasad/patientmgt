package com.cgm.app.service;

import com.cgm.app.dto.VisitDto;
import com.cgm.app.entity.Patient;
import com.cgm.app.entity.Visit;
import com.cgm.app.exception.BadRequestException;
import com.cgm.app.exception.ResourceNotFoundException;
import com.cgm.app.repository.PatientRepository;
import com.cgm.app.repository.VisitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VisitService {

    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;

    public VisitService(PatientRepository patientRepository, VisitRepository visitRepository) {
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
    }

    public VisitDto saveVisit(VisitDto visitDto) {
        if(visitDto.patientId() == null) {
            throw new BadRequestException("Patient cannot be null for visit request!");
        }
        Patient patient = patientRepository.findById(visitDto.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + visitDto.patientId()));
        Visit visit = new Visit();
        mapToVisit(visitDto, visit);
        visit.setPatient(patient);
        Visit savedVisit = visitRepository.save(visit);
        log.info("Created a new visit: {}", savedVisit);
        return convertToVisitDto(savedVisit);
    }

    public VisitDto updateVisit(Long visitId, VisitDto visitDto) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + visitId));
        mapToVisit(visitDto, visit);
        Visit updatedVisit = visitRepository.save(visit);
        log.info("Updated a visit: {}", updatedVisit);
        return convertToVisitDto(updatedVisit);
    }

    public VisitDto findByVisitId(Long visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + visitId));
        log.info("Fetching a visit id: {}", visitId);
        return convertToVisitDto(visit);
    }

    private void mapToVisit(VisitDto visitDto, Visit visit) {
        visit.setDateTime(visitDto.dateTime());
        visit.setVisitReason(visitDto.visitReason());
        visit.setVisitType(visitDto.visitType());
        visit.setFamilyHistory(visitDto.familyHistory());
    }

    private VisitDto convertToVisitDto(Visit visit) {
        return new VisitDto(visit.getId(), visit.getDateTime(), visit.getVisitReason(),
                visit.getVisitType(), visit.getFamilyHistory(),  visit.getPatient().getId());
    }
}

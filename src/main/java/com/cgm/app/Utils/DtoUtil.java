package com.cgm.app.Utils;

import com.cgm.app.dto.PatientDto;
import com.cgm.app.dto.VisitDto;
import com.cgm.app.entity.Patient;
import com.cgm.app.entity.Visit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DtoUtil {
    public static PatientDto convertToPatientDto(Patient patient) {
        List<VisitDto> visits = new ArrayList<>();
        if(!patient.getVisits().isEmpty()) {
            visits = patient.getVisits().stream().map(visit -> convertToVisitDto(visit)).collect(Collectors.toList());
        }
        return new PatientDto(patient.getId(), patient.getName(), patient.getSurname(),
                patient.getDateOfBirth(), patient.getSocialSecurityNumber(), visits);
    }

    public static VisitDto convertToVisitDto(Visit visit) {
        return new VisitDto(visit.getId(), visit.getDateTime(), visit.getVisitReason(),
                visit.getVisitType(), visit.getFamilyHistory(),  visit.getPatient().getId());
    }
}

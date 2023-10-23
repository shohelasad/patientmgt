package com.cgm.app.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record PatientDto(
        Long id,
        @NotNull(message = "name must not be null") String name,
        String surname,
        @NotNull(message = "Date Of Birth must not be null") LocalDate dateOfBirth,
        @NotNull(message = "Social Security Number must not be null") String socialSecurityNumber,
        List<VisitDto> visits
) {
}

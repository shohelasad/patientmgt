package com.cgm.app.dto;

import com.cgm.app.enums.VisitReason;
import com.cgm.app.enums.VisitType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VisitDto(
        Long id,
        @NotNull(message = "name must not be null")  LocalDateTime dateTime,
        @NotNull(message = "Visit reason not be null") VisitReason visitReason,
        @NotNull(message = "Visit type must not be null")  VisitType visitType,
        String familyHistory,
        @NotNull(message = "Patient must not be null")  Long patientId
) {
}

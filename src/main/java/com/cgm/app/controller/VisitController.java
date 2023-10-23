package com.cgm.app.controller;

import com.cgm.app.dto.VisitDto;
import com.cgm.app.service.VisitService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/visits")
@Validated
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public ResponseEntity<VisitDto> createVisit(@Valid @RequestBody VisitDto visit) {
        log.info("Creating a new visit with: {}", visit);
        VisitDto createdVisit = visitService.saveVisit(visit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVisit);
    }

    @GetMapping("/{visitId}")
    public ResponseEntity<VisitDto> getVisit(@PathVariable Long visitId) {
        log.info("Fetching a visit with: " + visitId);
        return ResponseEntity.ok(visitService.findByVisitId(visitId));
    }

    @PutMapping("/{visitId}")
    public ResponseEntity<VisitDto>  updateVisit(@PathVariable Long visitId, @RequestBody VisitDto visit) {
        log.info("Update a visit with: {}", visit);
        return ResponseEntity.ok(visitService.updateVisit(visitId, visit));
    }
}

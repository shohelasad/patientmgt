package com.cgm.app.entity;


import com.cgm.app.enums.VisitReason;
import com.cgm.app.enums.VisitType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Column(nullable = false)
    private VisitReason visitReason;
    private String familyHistory;
    @Column(nullable = false)
    private VisitType visitType;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}

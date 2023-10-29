package com.cgm.app.entity;


import com.cgm.app.enums.VisitReason;
import com.cgm.app.enums.VisitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Column(nullable = false)
    private VisitReason visitReason;
    @Column(nullable = false)
    private VisitType visitType;
    private String familyHistory;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}

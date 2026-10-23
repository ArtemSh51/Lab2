package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private CityReport report;

    @ManyToOne
    @JoinColumn(name = "crew_id")
    private WorkCrew crew;

    private LocalDateTime deadline;
    private String status;
    private String resultDescription;
}
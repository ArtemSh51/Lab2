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
public class CityReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_object_id")
    private CityObject cityObject;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private String description;
    private String status;

    private LocalDateTime createdAt;
}
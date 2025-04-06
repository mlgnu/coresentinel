package com.districtcore.coresentinel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crime_reports")
public class CrimeReport implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reportId;

    @Column(nullable = false)
    private String reporterName;

    @Column(nullable = false)
    private String reporterEmail;

    @Column(nullable = false)
    private String reporterId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reportDescription;

    @Column(nullable = false)
    private String location;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @ManyToOne()
    @JoinColumn(name = "case_id")
    @JsonBackReference
    private CrimeCase crimeCase;

    @PostPersist
    public void generateCustomId() {
        this.reportId = "R" + String.format("%03d", this.id);
    }
}

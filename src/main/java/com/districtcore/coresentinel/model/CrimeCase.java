package com.districtcore.coresentinel.model;


import com.districtcore.coresentinel.enums.CaseStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
//@EqualsAndHashCode(exclude = "reportedBy")
//@ToString(exclude = "reportedBy")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crime_cases")
public class CrimeCase implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caseId;

    @Column(nullable = false)
    private String caseName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String city;

    @ManyToOne()
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User createdBy;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    String caseType;

    @Column(nullable = false)
    String level;

    @Enumerated(EnumType.STRING)
    private CaseStatus status = CaseStatus.PENDING;

    @ManyToMany()
    @JoinTable(
            name = "case_assignees",
            joinColumns = @JoinColumn(name = "case_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> assignees;

    @ManyToMany(mappedBy = "crimeCases")
    Set<Person> persons;

    @OneToMany(mappedBy = "crimeCase")
    Set<Comment> comments;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "crimeCase", orphanRemoval = true)
    @JsonManagedReference
    Set<CrimeReport> reportedBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crimeCase", orphanRemoval = true)
    Set<Evidence> evidence;

    @PostPersist
    public void generateCustomId() {
        this.caseId = "C" + String.format("%03d", this.id);
    }
}

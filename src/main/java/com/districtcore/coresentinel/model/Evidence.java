package com.districtcore.coresentinel.model;

import com.districtcore.coresentinel.enums.EvidenceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE evidence SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Evidence implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String evidenceId;

    @ManyToOne()
    @JoinColumn(
            name = "case_id",
            nullable = false
    )
    private CrimeCase crimeCase;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EvidenceType type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String resource;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column
    private String imageSize;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean deleted = false;

    @PostPersist
    public void generateCustomId() {
        this.evidenceId = "E" + String.format("%03d", this.id);
    }
}

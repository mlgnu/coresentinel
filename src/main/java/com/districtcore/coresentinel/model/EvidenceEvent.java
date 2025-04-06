package com.districtcore.coresentinel.model;

import com.districtcore.coresentinel.enums.ResourceAction;
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
@Entity(name = "evidence_events")
public class EvidenceEvent implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long evidenceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private ResourceAction resourceAction;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User madeBy;
}

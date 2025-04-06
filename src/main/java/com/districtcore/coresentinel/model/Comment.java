package com.districtcore.coresentinel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentId;

    @Column(nullable = false)
    private String comment;


    @ManyToOne
    @JoinColumn(
            name = "case_id",
            nullable = false
    )
    private CrimeCase crimeCase;

    @ManyToOne()
    @JoinColumn(
            name = "created_by",
            nullable = false
    )
    private User createdBy;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PostPersist
    public void generateCustomId() {
        this.commentId = "C" + String.format("%03d", this.id);
    }
}

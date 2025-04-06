package com.districtcore.coresentinel.model;

import com.districtcore.coresentinel.enums.Gender;
import com.districtcore.coresentinel.enums.PersonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class Person implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String personId;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Integer age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PersonType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;

    @ManyToMany
    @JoinTable(
            name = "person_cases",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "case_id")
    )
    Set<CrimeCase> crimeCases;

    @PostPersist
    public void generateCustomId() {
        this.personId = "P" + String.format("%03d", this.id);
    }
}

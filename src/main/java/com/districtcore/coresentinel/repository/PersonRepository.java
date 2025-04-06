package com.districtcore.coresentinel.repository;

import com.districtcore.coresentinel.enums.PersonType;
import com.districtcore.coresentinel.model.CrimeCase;
import com.districtcore.coresentinel.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByCrimeCasesAndType(CrimeCase crimeCase, PersonType type);
}

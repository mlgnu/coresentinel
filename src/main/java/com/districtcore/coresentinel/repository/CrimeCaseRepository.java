package com.districtcore.coresentinel.repository;

import com.districtcore.coresentinel.model.CrimeCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrimeCaseRepository extends JpaRepository<CrimeCase, Long> {

    @Query(value = "SELECT regexp_matches( " +
            "  ( " +
            "    row_to_json(c)::text || " +
            "    COALESCE((SELECT json_agg(r)::text FROM crime_reports r WHERE r.case_id = c.id), '') || " +
            "    COALESCE((SELECT json_agg(e)::text FROM evidence e WHERE e.case_id = c.id), '') " +
            "  ), " +
            "  '(?:(?:https?://)|(?:www\\.))[^\\s\",}]+', " +
            "  'g' " +
            ") AS urls " +
            "FROM crime_cases c " +
            "WHERE c.id = :caseId", nativeQuery = true)
    List<String> findUrlsByCaseId(@Param("caseId") Long caseId);
}

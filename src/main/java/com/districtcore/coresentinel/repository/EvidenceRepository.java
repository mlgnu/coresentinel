package com.districtcore.coresentinel.repository;

import com.districtcore.coresentinel.dto.crimecase.WordFrequencyDto.WordFrequencyResponseDto;
import com.districtcore.coresentinel.enums.EvidenceType;
import com.districtcore.coresentinel.model.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

    @Modifying
    @Query(
            value = "DELETE FROM evidence WHERE id = :id",
            nativeQuery = true
    )
    void hardDelete(@Param("id") Long id);

    @Query(value = "SELECT word, nentry AS frequency " +
            "FROM ts_stat('SELECT tsv_resource FROM evidence') " +
            "ORDER BY frequency DESC " +
            "LIMIT 10", nativeQuery = true)
    List<WordFrequencyResponseDto> findTop10Words();

    List<Evidence> findByCrimeCaseIdAndTypeAndDeletedFalse(Long caseId, EvidenceType type);
}
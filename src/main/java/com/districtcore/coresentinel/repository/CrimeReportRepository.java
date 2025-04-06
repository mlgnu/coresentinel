package com.districtcore.coresentinel.repository;

import com.districtcore.coresentinel.model.CrimeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrimeReportRepository extends JpaRepository<CrimeReport, Long> {
//    Page<CrimeReport> findAll(Pageable pageable);
}

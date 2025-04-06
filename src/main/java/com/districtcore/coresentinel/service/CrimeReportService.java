package com.districtcore.coresentinel.service;

import com.districtcore.coresentinel.dto.crimecase.CrimeCaseStatusDto.CrimeCaseStatusResponseDto;
import com.districtcore.coresentinel.dto.crimereport.ReportCrimeDto.ReportCrimeRequest;
import com.districtcore.coresentinel.enums.CaseStatus;
import com.districtcore.coresentinel.mapper.CrimeReportMapper;
import com.districtcore.coresentinel.model.CrimeReport;
import com.districtcore.coresentinel.repository.CrimeReportRepository;
import com.districtcore.coresentinel.util.EntityPrefixUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CrimeReportService {
    private final CrimeReportRepository crimeReportRepository;
    private final CrimeReportMapper crimeReportMapper;

    public CrimeReportService(CrimeReportRepository crimeReportRepository, CrimeReportMapper crimeReportMapper) {
        this.crimeReportRepository = crimeReportRepository;
        this.crimeReportMapper = crimeReportMapper;
    }

    public String reportCrime(ReportCrimeRequest crimeReportDto) {
        CrimeReport crimeReport = crimeReportMapper.crimeReportDtoToCrimeReport(crimeReportDto);
        crimeReportRepository.save(crimeReport);
        return crimeReport.getReportId();
    }

    public Page<CrimeReport> getCrimeReports(Pageable pageable) {
        return this.crimeReportRepository.findAll(pageable);
    }

    public CrimeCaseStatusResponseDto getCrimeReportStatus(Long id) {
        CrimeReport crimeReport = this.crimeReportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not crime report found with Id: " + EntityPrefixUtils.getPrefixedId(CrimeReport.class, id)));

        CrimeCaseStatusResponseDto crimeCaseStatusResponseDto;
        if (crimeReport.getCrimeCase() == null) {
           crimeCaseStatusResponseDto = new CrimeCaseStatusResponseDto(CaseStatus.PENDING, "No Case is assigned to your report yet");
        } else {
           crimeCaseStatusResponseDto = new CrimeCaseStatusResponseDto(crimeReport.getCrimeCase().getStatus(),
                   "Case associated with your report: " + crimeReport.getCrimeCase().getCaseId());
        }
        return crimeCaseStatusResponseDto;
    }
}

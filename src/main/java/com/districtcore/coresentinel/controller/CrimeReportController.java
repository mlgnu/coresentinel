package com.districtcore.coresentinel.controller;

import com.districtcore.coresentinel.annotation.PrefixedId;
import com.districtcore.coresentinel.dto.crimecase.CrimeCaseStatusDto.CrimeCaseStatusResponseDto;
import com.districtcore.coresentinel.dto.crimereport.ReportCrimeDto.ReportCrimeRequest;
import com.districtcore.coresentinel.dto.crimereport.ReportCrimeDto.ReportCrimeResponse;
import com.districtcore.coresentinel.model.CrimeReport;
import com.districtcore.coresentinel.service.CrimeReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController()
@RequestMapping("/crime-reports")
@Tag(name = "Crime Reports")
public class CrimeReportController {

    private final CrimeReportService crimeReportService;

    public CrimeReportController(CrimeReportService crimeReportService) {
        this.crimeReportService = crimeReportService;
    }

    @PostMapping
    @Operation(summary = "Report a crime")
    public ResponseEntity<ReportCrimeResponse> reportCrime(@RequestBody @Valid ReportCrimeRequest crimeReport) {
        String crimeId = crimeReportService.reportCrime(crimeReport);
        System.out.println(crimeReport);
        ReportCrimeResponse response = new ReportCrimeResponse(crimeId);
        return ResponseEntity.created(URI.create("/" + crimeId)).body(response);
    }

    @GetMapping
    @Operation(summary = "List all crime reports")
    public ResponseEntity<Page<CrimeReport>> getCrimeReportStatus(
            @PrefixedId(prefix = "R") Long id, @ParameterObject Pageable pageable) {

        Page<CrimeReport> page = this.crimeReportService.getCrimeReports(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Get a crime report status by ID")
    public ResponseEntity<CrimeCaseStatusResponseDto> getCrimeReportStatus(@PrefixedId(prefix = "R") Long id) {
        CrimeCaseStatusResponseDto status = this.crimeReportService.getCrimeReportStatus(id);
        return ResponseEntity.ok(status);
    }
}

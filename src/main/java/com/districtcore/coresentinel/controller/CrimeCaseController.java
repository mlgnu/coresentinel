package com.districtcore.coresentinel.controller;

import com.districtcore.coresentinel.annotation.CurrentUserId;
import com.districtcore.coresentinel.annotation.PrefixedId;
import com.districtcore.coresentinel.dto.crimecase.CreateCaseDto.CreateCaseRequestDto;
import com.districtcore.coresentinel.dto.crimecase.CreateCaseDto.CreateCaseResponseDto;
import com.districtcore.coresentinel.dto.crimecase.GetAllCasesDto.GetAllCasesResponseDto;
import com.districtcore.coresentinel.dto.crimecase.GetPersonsWithTypeDto.GetPersonsWithTypeResponseDto;
import com.districtcore.coresentinel.enums.PersonType;
import com.districtcore.coresentinel.service.CrimeCaseService;
import com.districtcore.coresentinel.service.PdfReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/crime-cases")
@Tag(name = "Crime Cases")
public class CrimeCaseController {

    private final CrimeCaseService crimeCaseService;
    private final PdfReportService pdfReportService;

    public CrimeCaseController(CrimeCaseService crimeCaseService, PdfReportService pdfReportService) {
        this.crimeCaseService = crimeCaseService;
        this.pdfReportService = pdfReportService;
    }

    @PostMapping
    @Operation(summary = "Create a new crime case")
    public ResponseEntity<CreateCaseResponseDto> createCase(@RequestBody @Valid CreateCaseRequestDto createCaseRequestDto, @CurrentUserId Long id) throws BadRequestException {
        String caseId = this.crimeCaseService.createCase(createCaseRequestDto, id);
        return ResponseEntity.created(URI.create("/")).body(new CreateCaseResponseDto(caseId));
    }

    @GetMapping
    @Operation(summary = "Get all cases paginated with brief details")
    public ResponseEntity<Page<GetAllCasesResponseDto>> getAllCases(@ParameterObject Pageable pageable) {
        Page<GetAllCasesResponseDto> page = this.crimeCaseService.getAllCases(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}/links")
    @Operation(summary = "Get case links", description = "The search is done over the fields or the case as well as all the crime reports and evidence associated with it")
    public ResponseEntity<List<String>> getLinksByCaseId(@PrefixedId(prefix = "C") Long id) {
        List<String> urls = this.crimeCaseService.getUrlsByCaseId(id);
        return ResponseEntity.ok().body(urls);
    }

    @GetMapping("/{id}/witnesses")
    @Operation(summary = "Get witnesses by case ID", description = "Get all witnesses associated with a case")
    public ResponseEntity<List<GetPersonsWithTypeResponseDto>> getWitnessesByCaseId(@PrefixedId(prefix = "C") Long id) {
        List<GetPersonsWithTypeResponseDto> witnesses = this.crimeCaseService.getWitnessesWithTypeByCaseId(id, PersonType.WITNESS);
        return ResponseEntity.ok().body(witnesses);
    }

    @GetMapping("/{id}/suspects")
    @Operation(summary = "Get suspects by case ID", description = "Get all suspects associated with a case")
    public ResponseEntity<List<GetPersonsWithTypeResponseDto>> getSuspectsByCaseId(@PrefixedId(prefix = "C") Long id) {
        List<GetPersonsWithTypeResponseDto> suspects = this.crimeCaseService.getWitnessesWithTypeByCaseId(id, PersonType.SUSPECT);
        return ResponseEntity.ok().body(suspects);
    }

    @GetMapping("/{id}/victims")
    @Operation(summary = "Get victims by case ID", description = "Get all victims associated with a case")
    public ResponseEntity<List<GetPersonsWithTypeResponseDto>> getVictimsByCaseId(@PrefixedId(prefix = "C") Long id) {
        List<GetPersonsWithTypeResponseDto> victims = this.crimeCaseService.getWitnessesWithTypeByCaseId(id, PersonType.VICTIM);
        return ResponseEntity.ok().body(victims);
    }

    @GetMapping("/{id}/assignees")
    @Operation(summary = "Get assignees by case ID", description = "Get all assignees associated with a case")
    public ResponseEntity<List<GetPersonsWithTypeResponseDto>> getInvestigatorsByCaseId(@PrefixedId(prefix = "C") Long id) {
        List<GetPersonsWithTypeResponseDto> assignees = this.crimeCaseService.getAssignees(id);
        return ResponseEntity.ok().body(assignees);
    }

    @GetMapping("/{id}/pdf-report")
    @Operation(summary = "Get PDF report", description = "Get PDF report of the case")
    public ResponseEntity<byte[]> getPDFReport(@PrefixedId(prefix = "C") Long id, @CurrentUserId Long currentUserId) throws Exception {
        byte[] pdf = this.pdfReportService.generateReport(id, currentUserId);
        return ResponseEntity.ok().headers(
                headers -> headers.add("Content-Disposition", "attachment; filename=report.pdf")
        ).body(pdf);
    }
}

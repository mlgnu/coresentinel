package com.districtcore.coresentinel.service;

import com.districtcore.coresentinel.enums.EvidenceType;
import com.districtcore.coresentinel.mapper.PdfReportMapper;
import com.districtcore.coresentinel.model.CrimeCase;
import com.districtcore.coresentinel.model.Evidence;
import com.districtcore.coresentinel.repository.CrimeCaseRepository;
import com.districtcore.coresentinel.repository.EvidenceRepository;
import com.districtcore.coresentinel.util.pdf.Report.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdfReportFetcherService {

    private final CrimeCaseRepository crimeCaseRepository;
    private final PdfReportMapper pdfReportMapper;
    private final EvidenceRepository evidenceRepository;

    public PdfReportFetcherService(CrimeCaseRepository crimeCaseRepository, PdfReportMapper pdfReportMapper, EvidenceRepository evidenceRepository) {
        this.crimeCaseRepository = crimeCaseRepository;
        this.pdfReportMapper = pdfReportMapper;
        this.evidenceRepository = evidenceRepository;
    }

    public ReportMetaData fetchMetadata(Long caseId, Long currentUserId) {
        CrimeCase crimeCase = crimeCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));
        return pdfReportMapper.toReportMetaData(crimeCase, currentUserId);
    }

    public ReportCaseDetails fetchCaseDetails(Long caseId) {
        CrimeCase crimeCase = crimeCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));
        return pdfReportMapper.toReportCaseDetails(crimeCase);
    }

    public List<ReportTextEvidence> fetchTextEvidence(Long caseId) {
        List<Evidence> textEvidence = evidenceRepository.findByCrimeCaseIdAndTypeAndDeletedFalse(caseId, EvidenceType.TEXT);
        return textEvidence.stream().map(pdfReportMapper::toReportTextEvidence).toList();
    }

    public List<ReportImageEvidence> fetchImageEvidence(Long caseId) {
        List<Evidence> imageEvidence = evidenceRepository.findByCrimeCaseIdAndTypeAndDeletedFalse(caseId, EvidenceType.IMAGE);
        return imageEvidence.stream().map(pdfReportMapper::toReportImageEvidence).toList();
    }

    public List<ReportPersons> fetchPersons(Long caseId) {
        CrimeCase crimeCase =this.crimeCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));

       return crimeCase.getPersons().stream().map(pdfReportMapper::toPersons).toList();
    }

    public List<ReportOfficer> fetchOfficers(Long caseId) {
        CrimeCase crimeCase = this.crimeCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));

        return crimeCase.getAssignees().stream().map(pdfReportMapper::toOfficer).toList();
    }
}

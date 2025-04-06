package com.districtcore.coresentinel.mapper;

import com.districtcore.coresentinel.enums.ResourceAction;
import com.districtcore.coresentinel.model.CrimeCase;
import com.districtcore.coresentinel.model.Evidence;
import com.districtcore.coresentinel.model.Person;
import com.districtcore.coresentinel.model.User;
import com.districtcore.coresentinel.repository.CrimeCaseRepository;
import com.districtcore.coresentinel.repository.EvidenceEventRepository;
import com.districtcore.coresentinel.repository.UserRepository;
import com.districtcore.coresentinel.service.GoogleCloudStorageService;
import com.districtcore.coresentinel.util.pdf.Report.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { ReferenceMapper.class })
public abstract class PdfReportMapper {

    @Autowired
    protected CrimeCaseRepository crimeCaseRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected EvidenceEventRepository evidenceEventRepository;

    @Autowired
    GoogleCloudStorageService gcp;

    @Mapping(target = "generatedOn", expression = "java(java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern(\"dd MMM yyyy\")))")
    @Mapping(target = "version", constant = "1.0") // TODO: Create a versioning and caching system for reports
    @Mapping(target = "generatedBy", source = "currentUserId", qualifiedByName = "mapUserIdToFullName")
    abstract public ReportMetaData toReportMetaData(CrimeCase crimeCase, Long currentUserId);

    @Mapping(target = "name", source = "caseName")
    @Mapping(target = "date", source = "createdAt", dateFormat = "dd MMM yyyy")
    @Mapping(target = "creator", source = "id" , qualifiedByName = "mapReportGeneratedBy")
    abstract public ReportCaseDetails toReportCaseDetails(CrimeCase crimeCase);

    @Mapping(target = "evidence", source = "resource")
    @Mapping(target = "uploadedAt", source = "createdAt", dateFormat = "dd MMM yyyy")
    @Mapping(target = "uploadedBy", source = "id", qualifiedByName = "mapEvidenceIdToFullName")
    abstract public ReportTextEvidence toReportTextEvidence(Evidence evidence);

    @Mapping(target = "evidence", source = "resource", qualifiedByName = "mapEvidenceResourceUrlToImage")
    @Mapping(target = "uploadedAt", source = "createdAt", dateFormat = "dd MMM yyyy")
    @Mapping(target = "uploadedBy", source = "id", qualifiedByName = "mapEvidenceIdToFullName")
    abstract public ReportImageEvidence toReportImageEvidence(Evidence evidence);

    abstract public ReportPersons toPersons(Person person);

    @Mapping(target = "name", source = "fullName")
    abstract public ReportOfficer toOfficer(User user);

    @Named("mapUserIdToFullName")
    protected String mapUserIdToFullName(Long userId) {
        return userRepository.findById(userId)
                .map(User::getFullName)
                .orElse("Unknown User");
    }

    @Named("mapReportGeneratedBy")
    protected String mapReportGeneratedBy(Long caseId) {
        return crimeCaseRepository.findById(caseId)
                .map(crimeCase -> crimeCase.getCreatedBy().getFullName())
                .orElse("Unknown User");
    }

    @Named("mapEvidenceIdToFullName")
    protected String mapEvidenceIdToFullName(Long evidenceId) {
        return evidenceEventRepository.findByEvidenceIdAndResourceAction(evidenceId, ResourceAction.CREATE)
                .get(0).getMadeBy().getFullName();
    }

    @Named("mapEvidenceResourceUrlToImage")
    protected byte[] mapEvidenceResourceUrlToImage(String url) {
       return gcp.getFile(url).getImage();
    }


}

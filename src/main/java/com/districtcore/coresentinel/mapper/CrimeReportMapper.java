package com.districtcore.coresentinel.mapper;

import com.districtcore.coresentinel.dto.crimecase.GetAllCasesDto.CrimeCaseReportsResponseDto;
import com.districtcore.coresentinel.dto.crimereport.ReportCrimeDto.ReportCrimeRequest;
import com.districtcore.coresentinel.model.CrimeReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CrimeReportMapper {
    @Mapping(target = "reportDescription", source = "description")
    @Mapping(target = "occurredAt", source = "dateAndTime")
    @Mapping(source = "reporterDetails.id", target = "reporterId")
    @Mapping(source = "reporterDetails.email", target = "reporterEmail")
    @Mapping(source = "reporterDetails.name", target = "reporterName")
    CrimeReport crimeReportDtoToCrimeReport(ReportCrimeRequest crimeReportDto);

    @Mapping(target = "civil_id", source = "reporterId")
    @Mapping(target = "email", source = "reporterEmail")
    @Mapping(target = "name", source = "reporterName")
    CrimeCaseReportsResponseDto toDto(CrimeReport crimeReport);
}

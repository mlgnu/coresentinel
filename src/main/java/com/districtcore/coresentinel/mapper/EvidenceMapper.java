package com.districtcore.coresentinel.mapper;

import com.districtcore.coresentinel.dto.evidence.CreateEvidenceDto.CreateEvidenceRequestDto;
import com.districtcore.coresentinel.dto.evidence.GetEvidenceDto.GetEvidenceResponseDto;
import com.districtcore.coresentinel.dto.evidence.UpdateEvidenceDto.UpdateEvidenceRequestDto;
import com.districtcore.coresentinel.enums.EvidenceType;
import com.districtcore.coresentinel.model.Evidence;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { ReferenceMapper.class })
public interface EvidenceMapper {

    @Mapping(target = "resource", source = "text")
    @Mapping(target = "type", source = "text", qualifiedByName = "mapType")
    @Mapping(target = "crimeCase", source = "caseId")
    Evidence toEntity(CreateEvidenceRequestDto createEvidenceRequestDto);

    @Mapping(target = "id", source = "evidenceId")
    GetEvidenceResponseDto toDto(Evidence evidence);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "type", source = "text", qualifiedByName = "mapType")
    @Mapping(target = "crimeCase", source = "caseId")
    @Mapping(target = "resource", source = "text")
    void toUpdate(@MappingTarget Evidence evidence, UpdateEvidenceRequestDto updateEvidenceRequestDto);

    @Named("mapType")
    default EvidenceType mapType(String text) {
        return text == null ? EvidenceType.IMAGE : EvidenceType.TEXT;
    }
}

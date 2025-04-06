package com.districtcore.coresentinel.mapper;

import com.districtcore.coresentinel.enums.ResourceAction;
import com.districtcore.coresentinel.model.Evidence;
import com.districtcore.coresentinel.model.EvidenceEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ReferenceMapper.class })
public interface EvidenceEventMapper {
    @Mapping(target = "resourceAction", source = "action")
    @Mapping(target = "evidenceId", source = "evidence.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "madeBy", source = "userId")
    EvidenceEvent toEvent(Evidence evidence, ResourceAction action, Long userId);
}

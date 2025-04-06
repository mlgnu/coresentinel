package com.districtcore.coresentinel.repository;

import com.districtcore.coresentinel.enums.ResourceAction;
import com.districtcore.coresentinel.model.EvidenceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenceEventRepository extends JpaRepository<EvidenceEvent, Long> {
    List<EvidenceEvent> findByEvidenceIdAndResourceAction(Long evidenceId, ResourceAction resourceAction);
}

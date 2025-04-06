package com.districtcore.coresentinel.service;

import com.districtcore.coresentinel.dto.crimecase.WordFrequencyDto.WordFrequencyResponseDto;
import com.districtcore.coresentinel.dto.evidence.CreateEvidenceDto.CreateEvidenceRequestDto;
import com.districtcore.coresentinel.dto.evidence.GetEvidenceDto.GetEvidenceResponseDto;
import com.districtcore.coresentinel.dto.evidence.GetEvidenceDto.ImageEvidence;
import com.districtcore.coresentinel.dto.evidence.UpdateEvidenceDto.UpdateEvidenceRequestDto;
import com.districtcore.coresentinel.enums.EvidenceType;
import com.districtcore.coresentinel.mapper.EvidenceEventMapper;
import com.districtcore.coresentinel.mapper.EvidenceMapper;
import com.districtcore.coresentinel.model.Evidence;
import com.districtcore.coresentinel.repository.EvidenceEventRepository;
import com.districtcore.coresentinel.repository.EvidenceRepository;
import com.districtcore.coresentinel.util.FileUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EvidenceService {
    private final EvidenceRepository evidenceRepository;
    private final GoogleCloudStorageService gcs;
    private final EvidenceMapper evidenceMapper;
    private final EvidenceEventRepository evidenceEventRepository;
    private final EvidenceEventMapper evidenceEventMapper;
    private final EvidenceDeletionService evidenceDeletionService;
    private final TransactionTemplate transactionTemplate;

    public EvidenceService(EvidenceRepository evidenceRepository, GoogleCloudStorageService gcs,
                           EvidenceMapper evidenceMapper, EvidenceEventRepository evidenceEventRepository,
                           EvidenceEventMapper evidenceEventMapper, EvidenceDeletionService evidenceDeletionService, TransactionTemplate transactionTemplate) {
        this.evidenceRepository = evidenceRepository;
        this.gcs = gcs;
        this.evidenceMapper = evidenceMapper;
        this.evidenceEventRepository = evidenceEventRepository;
        this.evidenceEventMapper = evidenceEventMapper;
        this.evidenceDeletionService = evidenceDeletionService;
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    public String createEvidence(CreateEvidenceRequestDto request) {
        Evidence evidence = this.evidenceMapper.toEntity(request);
        if (request.getImage() != null) {
            String objectKey = this.gcs.uploadFile(request.getImage());
            evidence.setResource(objectKey);
            evidence.setImageSize(FileUtils.getFormattedFileSize(request.getImage()));
        }
        this.evidenceRepository.save(evidence);
        return evidence.getEvidenceId();
    }

    public Evidence getEvidenceNotSoftDeleted(Long id) {
        Evidence evidence = this.evidenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Evidence Not found"));
        if (evidence.getDeleted()) {
            throw new EntityNotFoundException("Evidence is soft deleted, contact admin to restore");
        }
        return evidence;
    }

    public GetEvidenceResponseDto getEvidenceDetails(Long id) {
        Evidence evidence = getEvidenceNotSoftDeleted(id);
        return this.evidenceMapper.toDto(evidence);
    }

    @Transactional
    public void updateEvidence(UpdateEvidenceRequestDto updateEvidenceRequestDto, Long id) {
        Evidence evidence = getEvidenceNotSoftDeleted(id);
        EvidenceType requestEvidenceType = updateEvidenceRequestDto.getImage() != null ? EvidenceType.IMAGE : EvidenceType.TEXT;

        if (requestEvidenceType != evidence.getType()) {;
            throw new IllegalArgumentException("Evidence type cannot be changed");
        }

        this.evidenceMapper.toUpdate(evidence, updateEvidenceRequestDto);

        if (requestEvidenceType == EvidenceType.IMAGE) {
            String objectKey = this.gcs.uploadFile(updateEvidenceRequestDto.getImage());
            String previousObjectKey = evidence.getResource();
            evidence.setResource(objectKey);
            evidence.setImageSize(FileUtils.getFormattedFileSize(updateEvidenceRequestDto.getImage()));
            this.gcs.deleteFile(previousObjectKey);
        }
        this.evidenceRepository.save(evidence);
    }

    @Transactional
    public void softDeleteEvidence(Long id, Long userId) {
//       Evidence evidence = getEvidenceNotSoftDeleted(id);
       this.evidenceRepository.deleteById(id);
//       EvidenceEvent event = evidenceEventMapper.toEvent(evidence, ResourceAction.SOFT_DELETE, userId);
//       this.evidenceEventRepository.save(event);
    }

    public ImageEvidence getEvidenceImageById(Long id) {
        Evidence evidence = getEvidenceNotSoftDeleted(id);

        if (evidence.getType() != EvidenceType.IMAGE) {
            throw new IllegalArgumentException("Evidence is not an image");
        }

        return this.gcs.getFile(evidence.getResource());
    }

    public List<WordFrequencyResponseDto> getEvidenceFrequencies() {
        return this.evidenceRepository.findTop10Words();
    }

    @Transactional
    public void initHardDeletion(String id, String token) {
        CompletableFuture.runAsync(() -> {
            transactionTemplate.execute(status -> {
                try {
                    evidenceDeletionService.sendStatus(token, "IN_PROGRESS");

                    evidenceRepository.hardDelete(Long.parseLong(id));

                    evidenceDeletionService.sendStatus(token, "SUCCESS");
                    return null;
                } catch (Exception ex) {
                    evidenceDeletionService.sendStatus(token, "FAILED: " + ex.getMessage());
                    status.setRollbackOnly();
                    return null;
                }
            });
        });
    }

    public void registerEmitter(String id, SseEmitter emitter) {
        evidenceDeletionService.registerEmitter(id, emitter);
    }
}

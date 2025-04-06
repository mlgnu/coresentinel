package com.districtcore.coresentinel.controller;

import com.districtcore.coresentinel.annotation.CurrentUserId;
import com.districtcore.coresentinel.annotation.PrefixedId;
import com.districtcore.coresentinel.dto.crimecase.WordFrequencyDto.WordFrequencyResponseDto;
import com.districtcore.coresentinel.dto.evidence.CreateEvidenceDto;
import com.districtcore.coresentinel.dto.evidence.CreateEvidenceDto.CreateEvidenceResponseDto;
import com.districtcore.coresentinel.dto.evidence.GetEvidenceDto.GetEvidenceResponseDto;
import com.districtcore.coresentinel.dto.evidence.GetEvidenceDto.ImageEvidence;
import com.districtcore.coresentinel.dto.evidence.HardDeleteEvidenceDto.HardDeleteEvidenceConfirmResponseDto;
import com.districtcore.coresentinel.dto.evidence.UpdateEvidenceDto;
import com.districtcore.coresentinel.service.EvidenceDeletionConfirmationService;
import com.districtcore.coresentinel.service.EvidenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/evidence")
@Tag(name = "Evidence", description = "Manage evidence of crime cases")
public class EvidenceController {

    private final EvidenceService evidenceService;
    private EvidenceDeletionConfirmationService evidenceDeletionConfirmationService;

    public EvidenceController(EvidenceService evidenceService, EvidenceDeletionConfirmationService evidenceDeletionConfirmationService) {
        this.evidenceService = evidenceService;
        this.evidenceDeletionConfirmationService = evidenceDeletionConfirmationService;
    }

    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "Upload evidence", description = "Upload either an image or text as evidence")
    public ResponseEntity<CreateEvidenceResponseDto> uploadEvidence(@Valid @ModelAttribute CreateEvidenceDto.CreateEvidenceRequestDto request) {
        String evidenceId = this.evidenceService.createEvidence(request);
        return ResponseEntity.created(URI.create("/" + evidenceId)).build();
    }

    @PatchMapping(consumes = "multipart/form-data")
    @Operation(summary = "Update evidence", description = "Update evidence by Id")
    public ResponseEntity<Void> updateEvidence(@Valid @ModelAttribute UpdateEvidenceDto.UpdateEvidenceRequestDto request) {
        System.out.println(request);
        this.evidenceService.updateEvidence(request, 1L);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get evidence", description = "Get evidence by id")
    public ResponseEntity<GetEvidenceResponseDto> getEvidence(@PrefixedId(prefix = "E") Long id) {
        GetEvidenceResponseDto evidenceDto = this.evidenceService.getEvidenceDetails(id);
        return ResponseEntity.ok(evidenceDto);
    }

    @GetMapping("{id}/image")
    @Operation(summary = "Get image evidence", description = "Get image evidence by id")
    public ResponseEntity<byte[]> getImageEvidence(@PrefixedId(prefix = "E") Long id) {
        ImageEvidence evidence = this.evidenceService.getEvidenceImageById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, evidence.getContentType())
                .body(evidence.getImage());
    }

    @GetMapping("/frequencies")
    @Operation(summary = "Get evidence frequencies", description = "Get top 10 most frequent words in text evidence")
    public ResponseEntity<List<WordFrequencyResponseDto>> getEvidenceFrequencies() {
        return ResponseEntity.ok().body(this.evidenceService.getEvidenceFrequencies());
    }

    @DeleteMapping("/soft-delete/{id}")
    @Operation(summary = "Soft delete evidence", description = "Soft delete evidence by id")
    public ResponseEntity<Void> softDeleteEvidence(@PrefixedId(prefix = "E") Long id, @CurrentUserId Long userId) {
        this.evidenceService.softDeleteEvidence(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/hard-delete/{id}/confirm")
    @Operation(summary = "Confirmation token for hard deletion",
            description = "Returns a confirmation token for hard deletion of evidence to be used in the hard delete endpoint, valid for 5 minutes")
    public ResponseEntity<HardDeleteEvidenceConfirmResponseDto>
        hardDeleteEvidenceConfirmation(@PrefixedId(prefix = "E") Long id)
    {
        String token = this.evidenceDeletionConfirmationService.createDeleteToken(id);
        return ResponseEntity.ok().body(new HardDeleteEvidenceConfirmResponseDto(token));
    }

    @DeleteMapping("/hard-delete/{token}")
    @Operation(summary = "Hard delete evidence", description = "Hard delete evidence by id")
    public ResponseEntity<String> hardDeleteEvidence(
            @PathVariable("token") String token
    ) {
        String id = String.valueOf(this.evidenceDeletionConfirmationService.getEvidenceIdFromToken(token));

        if (id == null) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        this.evidenceService.initHardDeletion(id, token);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "deletion-status/sse/{token}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Stream evidence deletion status",
            description = "Streams the status of the evidence deletion process using SSE. A delay of 2 seconds has been added before each status for simulation" +
                    " Note: swagger doesn't officially support SSE, therefore, you may see all the updates in one big response without streaming." +
                    " Use a tool like Postman or curl to properly test this endpoint.")
    public SseEmitter streamStatus(@PathVariable String token) {
        SseEmitter emitter = new SseEmitter(120_000L);
        this.evidenceService.registerEmitter(token, emitter);
        return emitter;
    }

}

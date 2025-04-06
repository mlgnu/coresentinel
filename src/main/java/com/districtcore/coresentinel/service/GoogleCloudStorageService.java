package com.districtcore.coresentinel.service;

import com.districtcore.coresentinel.dto.evidence.GetEvidenceDto.ImageEvidence;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GoogleCloudStorageService {

    @Value("${spring.cloud.gcp.storage.bucket-name}")
    private String bucketName;

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    public String uploadFile(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();
            storage.create(blobInfo, file.getBytes());
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to GCS", e);
        }
    }

    public void deleteFile(String objectKey) {
        storage.delete(bucketName, objectKey);
    }

    public ImageEvidence getFile(String objectKey) {
        Blob blob = storage.get(BlobId.of(bucketName, objectKey));

        if (blob == null) {
            throw new RuntimeException("File not found");
        }

        byte[] content = blob.getContent();
        String contentType = blob.getContentType();

        return new ImageEvidence(contentType, content);
    }
}

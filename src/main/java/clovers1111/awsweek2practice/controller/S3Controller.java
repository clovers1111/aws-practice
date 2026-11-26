package clovers1111.awsweek2practice.controller;

import clovers1111.awsweek2practice.storage.S3StorageService;
import clovers1111.awsweek2practice.domain.PresignedExtractedPutResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;


import java.io.IOException;

@RestController
@RequestMapping("/s3")
class S3Controller {

    private final S3StorageService localStorageService;

    public S3Controller(S3StorageService localStorageService) {

        this.localStorageService = localStorageService;
    }

    @PostMapping(path = "/upload")
    @Valid
    public PutObjectResponse uploadFile(@RequestParam("file") @NotNull MultipartFile mpFile, @RequestParam String key) throws IOException {
        return this.localStorageService.upload(mpFile, mpFile.getBytes().length, key);
    }

    @PostMapping(path = "/request-presigned")
    public PresignedExtractedPutResponse requestPresigned(@NotNull @RequestParam String key) {
        return this.localStorageService.generatePresignRequest(key, null);
    }
}

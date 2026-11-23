package clovers1111.awsweek2practice.storage.domain;

import software.amazon.awssdk.awscore.presigner.PresignedRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

public record PresignedExtractedPutResponse(String url) {

    public static PresignedExtractedPutResponse from(PresignedRequest request) {
        return new PresignedExtractedPutResponse(request.url().toString());
    }
}

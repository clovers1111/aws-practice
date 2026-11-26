package clovers1111.awsweek2practice.domain;

import software.amazon.awssdk.awscore.presigner.PresignedRequest;

public record PresignedExtractedPutResponse(String url) {

    public static PresignedExtractedPutResponse from(PresignedRequest request) {
        return new PresignedExtractedPutResponse(request.url().toString());
    }
}

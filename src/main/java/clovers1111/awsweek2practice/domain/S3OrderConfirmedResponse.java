package clovers1111.awsweek2practice.domain;

public record S3OrderConfirmedResponse(String s3UploadeUrl) {
    public static S3OrderConfirmedResponse from(String s3UploadUrl) {
        return new S3OrderConfirmedResponse(s3UploadUrl);
    }
}

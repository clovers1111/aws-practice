package clovers1111.awsweek2practice.domain;

public record S3OrderPresignedCreationResponse(String s3UploadUrl, String orderUUID) {
    public static S3OrderPresignedCreationResponse from(String s3UploadUrl, String orderUUID) {
        return new S3OrderPresignedCreationResponse(s3UploadUrl, orderUUID);
    }
}

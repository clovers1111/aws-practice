package clovers1111.awsweek2practice.storage;

import clovers1111.awsweek2practice.config.S3BucketProperties;
import clovers1111.awsweek2practice.storage.domain.PresignedExtractedPutResponse;
import jakarta.annotation.Nullable;
import org.hibernate.validator.constraints.time.DurationMax;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.presigner.PresignedRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.*;
import java.time.Duration;

@Service
public class S3StorageService {

    final S3Client s3Client;

    final S3BucketProperties s3Bucket;

    private final S3Presigner s3Presigner;

    final static int MAX_DURATION_MINUTES = 15;

    final static int BUFFER_SIZE = 8192;

    // Know to initialize through bean configuration
    public S3StorageService(S3Client s3Client, S3BucketProperties s3Bucket, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Bucket = s3Bucket;
        this.s3Presigner = s3Presigner;
    }

    public PresignedExtractedPutResponse generatePresignRequest(String key, @Nullable @DurationMax(minutes = MAX_DURATION_MINUTES) Duration duration) {
        if (duration == null) {
            duration = Duration.ofMinutes(MAX_DURATION_MINUTES);
        }
        final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3Bucket.url())
                .key(key)
                .build();

        final PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(duration)
                .putObjectRequest(putObjectRequest)
                .build();

        return PresignedExtractedPutResponse.from(s3Presigner.presignPutObject(presignRequest));

    }

    public PutObjectResponse upload(InputStreamSource inputStreamSource, long size, String key) throws IOException {
        return uploadFromInputStream(inputStreamSource.getInputStream(), size, key);
    }

    public PutObjectResponse upload(InputStreamSource inputStreamSource, String key) throws IOException {
        final InputStream inputStream = inputStreamSource.getInputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int intermittentBytes;
        long bytesRead = 0;
        // return n-1 will give <= BUFFER_SIZE bytes after hitting EOF -- return n will be -1 and skips next while
        while ((intermittentBytes = inputStream.read(buffer)) != -1) {
            bytesRead += intermittentBytes;
        }
        return uploadFromInputStream(inputStream, bytesRead, key);
    }



    public <T extends Serializable> PutObjectResponse upload(T object, String key) throws IOException {
        final byte[] bytes;
        // Oos takes the object and serializes it into baos.
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            oos.flush();
            bytes = baos.toByteArray();
        }
        return uploadFromInputStream(new ByteArrayInputStream(bytes), bytes.length, key);
    }


    private PutObjectResponse uploadFromInputStream(InputStream inputStream, long size, String key) {
        return s3Client.putObject(PutObjectRequest.builder()
                .bucket(s3Bucket.url())
                .key(key)
                .build(), RequestBody.fromInputStream(inputStream, size));

    }


}

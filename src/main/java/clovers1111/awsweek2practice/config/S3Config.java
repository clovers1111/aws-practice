package clovers1111.awsweek2practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client(S3ClientProperties properties) {
        final AwsBasicCredentials credentials = AwsBasicCredentials.create(properties.accessKey(), properties.secretKey());
        return S3Client.builder()
                .region(properties.region())
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

    }

    @Bean
    public S3Presigner s3Presigner(S3ClientProperties properties) {
        final AwsBasicCredentials credentials = AwsBasicCredentials.create(properties.accessKey(), properties.secretKey());
        return S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(properties.region())
                .build();
    }

}

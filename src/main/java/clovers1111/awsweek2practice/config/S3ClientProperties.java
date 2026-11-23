package clovers1111.awsweek2practice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import software.amazon.awssdk.regions.Region;

@ConfigurationProperties(prefix = "aws.s3.client")
@Validated
public record S3ClientProperties(Region region, String accessKey, String secretKey) {}

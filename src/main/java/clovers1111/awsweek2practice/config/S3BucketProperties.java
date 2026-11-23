package clovers1111.awsweek2practice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.s3.server")
public record S3BucketProperties (String url) {}

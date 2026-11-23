package clovers1111.awsweek2practice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.local")
public record LocalProperties(String directory) {}

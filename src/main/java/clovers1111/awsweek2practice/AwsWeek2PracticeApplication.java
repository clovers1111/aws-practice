package clovers1111.awsweek2practice;

import clovers1111.awsweek2practice.config.LocalProperties;
import clovers1111.awsweek2practice.config.S3BucketProperties;
import clovers1111.awsweek2practice.config.S3ClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@EnableConfigurationProperties({S3ClientProperties.class, S3BucketProperties.class, LocalProperties.class})
public class AwsWeek2PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsWeek2PracticeApplication.class, args);
    }

}

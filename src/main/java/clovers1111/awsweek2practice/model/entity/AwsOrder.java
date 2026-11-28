package clovers1111.awsweek2practice.model.entity;

import clovers1111.awsweek2practice.domain.S3UploadStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Data
@AllArgsConstructor
public class AwsOrder {

    @Id
    private String orderId;

    @Column
    private String bucket;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private S3UploadStatus uploadStatus = S3UploadStatus.PENDING;

    public AwsOrder() {

    }

}

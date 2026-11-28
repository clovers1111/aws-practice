package clovers1111.awsweek2practice.order;

import clovers1111.awsweek2practice.config.S3BucketProperties;
import clovers1111.awsweek2practice.domain.OrderCreationRequest;
import clovers1111.awsweek2practice.domain.S3OrderConfirmationResponse;
import clovers1111.awsweek2practice.domain.S3OrderPresignedCreationResponse;
import clovers1111.awsweek2practice.model.entity.AwsOrder;
import clovers1111.awsweek2practice.repository.OrderRepository;
import clovers1111.awsweek2practice.storage.S3StorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderCreationService {

    private final OrderRepository orderRepository;
    private final S3StorageService s3StorageService;
    private final S3BucketProperties s3BucketProperties;

    public OrderCreationService(OrderRepository orderRepository, S3StorageService s3StorageService, S3BucketProperties s3BucketProperties) {
        this.orderRepository = orderRepository;
        this.s3StorageService = s3StorageService;
        this.s3BucketProperties = s3BucketProperties;
    }

    @Transactional
    public S3OrderConformationResponse checkS3Upload(String uuid) {

        // Check database first

        // Check s3
        return s3StorageService.getHeadObjectFromKey(uuid)
                .ifPresentOrElse(headObject -> {
            new S3OrderConfirmationResponse()
        })
    }

    @Transactional
    public S3OrderPresignedCreationResponse createOrderWithPresigned(OrderCreationRequest orderCreationRequest) {
        // Even though a collision is very rare, should probably be a shared library function to generate a new order number that is unique
        final String uuid = UUID.randomUUID().toString();
        final String s3UploadUrl = this.s3StorageService.generatePresignRequest(uuid, null).url();


        final AwsOrder order = AwsOrder.builder().orderId(uuid)
                .description(orderCreationRequest.description())
                .bucket(s3BucketProperties.url())
                .build();


        try {
            this.orderRepository.save(order);
        } catch (Exception e) {
            // some failure occurred during order creation
            // s3StorageService.revoke()
        }

        return S3OrderPresignedCreationResponse.from(s3UploadUrl, uuid);
    }
}

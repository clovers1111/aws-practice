package clovers1111.awsweek2practice.order;

import clovers1111.awsweek2practice.config.S3BucketProperties;
import clovers1111.awsweek2practice.domain.OrderCreationRequest;
import clovers1111.awsweek2practice.domain.S3OrderConfirmedResponse;
import clovers1111.awsweek2practice.model.entity.Order;
import clovers1111.awsweek2practice.repository.OrderRepository;
import clovers1111.awsweek2practice.storage.S3StorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.S3Client;

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
    public S3OrderConfirmedResponse createOrderWithPresigned(OrderCreationRequest orderCreationRequest) {
        final String uuid = UUID.randomUUID().toString();
        final String s3UploadUrl = this.s3StorageService.generatePresignRequest(uuid, null).url();


        final Order order = Order.builder().orderId(null)
                .description(orderCreationRequest.description())
                .bucket(s3BucketProperties.url())
                .key(uuid)
                .build();

        try {
            this.orderRepository.save(order);
        } catch (Exception e) {
            // some failure occurred during order creation
            // s3StorageService.revoke()
        }

        return S3OrderConfirmedResponse.from(s3UploadUrl);
    }
}

package clovers1111.awsweek2practice.controller;

import clovers1111.awsweek2practice.domain.OrderConfirmationResponse;
import clovers1111.awsweek2practice.domain.S3OrderPresignedCreationResponse;
import clovers1111.awsweek2practice.domain.OrderCreationRequest;
import clovers1111.awsweek2practice.order.OrderConfirmationService;
import clovers1111.awsweek2practice.order.OrderCreationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
class OrderController {

    private final OrderCreationService orderCreationService;

    private final OrderConfirmationService orderConfirmationService;

    public OrderController(OrderCreationService orderCreationService, OrderConfirmationService orderConfirmationService) {

        this.orderCreationService = orderCreationService;
        this.orderConfirmationService = orderConfirmationService;
    }

    // Create an order by just hitting this endpoint; get back an s3 bucket link to upload data
    @PostMapping(path = "/create")
    public S3OrderPresignedCreationResponse createOrder(@RequestBody OrderCreationRequest orderCreationRequest, @RequestParam(required = false) boolean s3PresignedUrl) {
        return orderCreationService.createOrderWithPresigned(orderCreationRequest);
    }

    @GetMapping(path = "/check-order")
    public OrderConfirmationResponse checkOrderStatus(@RequestParam String order) {
        orderConfirmationService.checkOrder(order);

    }

}

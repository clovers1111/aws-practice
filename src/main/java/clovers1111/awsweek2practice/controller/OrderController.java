package clovers1111.awsweek2practice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/order")
class OrderController {

    @PostMapping(path = "/create")
    public OrderConfirmedResponse createOrder() {

    }

}

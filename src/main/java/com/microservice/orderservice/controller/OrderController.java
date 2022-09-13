package com.microservice.orderservice.controller;

import com.microservice.orderservice.model.TransactionRequest;
import com.microservice.orderservice.model.TransactionResponse;
import com.microservice.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private Logger log = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/bookorder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request){
        log.info("START : inside OrderController - bookOrder method!");
        return orderService.saveOrder(request);
    }

    @PostMapping("/saveorder")
    public TransactionResponse saveOrder(@RequestBody TransactionRequest  request) {
        return null;
    }
}

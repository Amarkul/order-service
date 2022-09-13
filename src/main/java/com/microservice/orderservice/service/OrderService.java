package com.microservice.orderservice.service;

import com.microservice.orderservice.controller.OrderController;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.Payment;
import com.microservice.orderservice.model.TransactionRequest;
import com.microservice.orderservice.model.TransactionResponse;
import com.microservice.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    @Lazy
    RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String endpointUrl;

    private Logger log = LoggerFactory.getLogger(OrderService.class);

    public TransactionResponse saveOrder(TransactionRequest request){

        log.info("START : inside OrderService - saveOrder method");
        String responseMessage;
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setOrderPrice(order.getPrice());
        log.info("Order data is - {}",order);

        try {
            Payment paymentResponse = restTemplate.postForObject(endpointUrl, payment, Payment.class);

            if (paymentResponse!=null){
                responseMessage = paymentResponse.getStatus().equals("success") ? "Payment processing successful" : "there is a failure, order added to cart";
                log.info("Response Message is - {}",responseMessage);
                orderRepository.save(order);
                log.info("END : inside OrderService - saveOrder method");
                return new TransactionResponse(order, paymentResponse.getTransactionId(), paymentResponse.getOrderPrice(),responseMessage);
            }
        } catch (Exception e){
            log.error("Exception occorred due to - {}",e.getCause());
        }
       return null;
    }
}

package com.ecommerce.notification;

import com.ecommerce.notification.payload.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class OrderEventConsumer {

    // The bean name "orderCreated" is the function name:
    // spring.cloud.function.definition: orderCreated  ->  binding "orderCreated-in-0"
    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            log.info("Received order created event for order: {}", event.getOrderId());
            log.info("Received order created event for user id: {}", event.getUserId());
        };
    }
}

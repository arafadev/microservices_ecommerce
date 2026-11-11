package com.ecommerce.notification;

import com.ecommerce.notification.payload.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class OrderEventConsumer {

    // ep 292: the old @RabbitListener consumer, replaced by the Spring Cloud Function bean below.
    //
    // @RabbitListener(queues = "${rabbitmq.queue.name}")
    // public void handleOrderEvent(OrderCreatedEvent orderEvent){
    //     System.out.println("Received Order Event: " + orderEvent);
    //
    //     long orderId = orderEvent.getOrderId();
    //     OrderStatus orderStatus = orderEvent.getStatus();
    //
    //     System.out.println("Order ID: " + orderId);
    //     System.out.println("Order Status: " + orderStatus);
    //
    //     // Update Database
    //     // Send Notification
    //     // Send Emails
    //     // Generate Invoice
    //     // Send Seller Notification
    // }

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

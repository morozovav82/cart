package ru.morozov.cart.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.morozov.cart.service.CartService;
import ru.morozov.messages.OrderCreatedMsg;

@Component
@Slf4j
@RequiredArgsConstructor
@RabbitListener(queues = "${active-mq.OrderCreated-topic}")
public class OrderCreatedConsumer {

    private final CartService cartService;

    @RabbitHandler
    public void receive(OrderCreatedMsg msg) {
        log.info("Received Message: {}", msg.toString());
        try {
            cartService.clear(msg.getOrderId(), msg.getUserId());
        } catch (Exception e) {
            log.error("Failed to save cart", e);
        }
    }
}

package ru.morozov.cart.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.morozov.cart.service.CartService;
import ru.morozov.messages.OrderCreatedMsg;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedConsumer implements MessageListener {

    private final CartService cartService;

    private ObjectMessage receiveMessage(Message message) {
        ObjectMessage objectMessage;

        try {
            objectMessage = (ObjectMessage) message;
            log.info("Received Message: {}", objectMessage.getObject().toString());
            return objectMessage;
        } catch (Exception e) {
            log.error("Failed to receive message", e);
            return null;
        }
    }

    @Override
    @JmsListener(destination = "${active-mq.OrderCreated-topic}")
    public void onMessage(Message message) {
        ObjectMessage objectMessage = receiveMessage(message);
        if (objectMessage == null) return;

        try {
            OrderCreatedMsg msg = (OrderCreatedMsg) objectMessage.getObject();
            cartService.clear(msg.getOrderId(), msg.getUserId());
        } catch (Exception e) {
            log.error("Failed to save bill", e);
        }
    }
}

package ru.morozov.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.morozov.messages.OrderCreatedMsg;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${active-mq.OrderCreated-topic}")
    private String orderCreatedTopic;

    private void sendMessage(String topic, Object message){
        try{
            log.info("Attempting send message to Topic: "+ topic);
            rabbitTemplate.convertAndSend(topic, message);
            log.info("Message sent: {}", message);
        } catch(Exception e){
            log.error("Failed to send message", e);
        }
    }

    @PostMapping("/sendOrderCreatedMsg")
    public void sendOrderCreatedMsg(@RequestBody OrderCreatedMsg message) {
        sendMessage(orderCreatedTopic, message);
    }
}

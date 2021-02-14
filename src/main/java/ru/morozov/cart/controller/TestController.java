package ru.morozov.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.morozov.cart.service.MessageService;
import ru.morozov.messages.OrderCreatedMsg;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @Autowired
    private MessageService messageService;

    @Value("${active-mq.OrderCreated-topic}")
    private String orderCreatedTopic;

    @PostMapping("/sendOrderCreatedMsg")
    public void sendOrderCreatedMsg(@RequestBody OrderCreatedMsg message) {
        messageService.scheduleSentMessage(orderCreatedTopic, null, message, OrderCreatedMsg.class);
    }
}

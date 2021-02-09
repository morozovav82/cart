package ru.morozov.cart.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    @Value("${active-mq.OrderCreated-topic}")
    private String orderCreatedTopic;

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(orderCreatedTopic);
    }
}

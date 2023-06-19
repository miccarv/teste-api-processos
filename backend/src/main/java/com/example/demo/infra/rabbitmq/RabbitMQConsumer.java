package com.example.demo.infra.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.demo.model.Processo;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = {"${rabbitmq.queue}"})
    public void receive(@Payload Processo processo) {
        System.out.println(processo.getNumero());
    }

}
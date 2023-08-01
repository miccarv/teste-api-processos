package com.miccarv.project.infra.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.miccarv.project.model.Processo;
import com.miccarv.project.service.ProcessoService;

@Component
public class RabbitMQConsumer {

    private ProcessoService processoService;

    public RabbitMQConsumer(ProcessoService processoService) {
        this.processoService = processoService;
    }

    @RabbitListener(queues = { "${rabbitmq.queue}" })
    public void receive(@Payload Processo processo) throws IOException {
        processoService.create(processo);
        System.out.println(processo.getNumero());
    }

}
package com.miccarv.project.infra.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.miccarv.project.infra.elasticsearch.model.ProcessoDoc;
import com.miccarv.project.infra.elasticsearch.service.ESProcessoService;

@Component
public class RabbitMQConsumer {

    private ESProcessoService eSProcessoService;

    public RabbitMQConsumer(ESProcessoService eSProcessoService) {
        this.eSProcessoService = eSProcessoService;
    }

    @RabbitListener(queues = { "${rabbitmq.queue}" })
    public void receive(@Payload ProcessoDoc processoDoc) throws IOException {
        eSProcessoService.create(processoDoc);
        System.out.println(processoDoc.getNumero());
    }

}
package com.derso.architecture.sagastest;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListener {
    
    @RabbitListener(queues = "${service.rabbitmq-queue}")
    public void messageReceived(String timeoutMessage) {
        System.out.println("Recebida mensagem de timeout: " + timeoutMessage);
    }

}

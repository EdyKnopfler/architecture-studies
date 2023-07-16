package com.derso.architecture.sagastest;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TimeoutListener {
    
    @RabbitListener(queues = "${timeout.rabbitmq-queue}")
    public void timeoutReceived(String timeoutMessage) {
        System.out.println("Recebida mensagem de timeout: " + timeoutMessage);
    }

}

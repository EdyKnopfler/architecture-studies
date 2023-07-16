package com.derso.architecture.timeout;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

/*
 * Refs.:
 * 
 * Agendamento de tarefas com Spring Boot
 * https://docs.spring.io/spring-framework/reference/integration/scheduling.html
 * https://stackoverflow.com/questions/30347233/spring-scheduling-task-run-only-once
 * 
 * RabbitMQ com Spring Boot (se não funcionasse usaria a lib cliente de AMQP)
 * https://spring.io/guides/gs/messaging-rabbitmq/
 */

@Component
public class TimeoutTask {
    
    private static final long TIMEOUT_SECONDS = 10;
    private static final String exchange = "architecture-studies";
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Async
    public void scheduleTimeouts(long itemId, String service) {
        ScheduledExecutorService localExecutor = 
            Executors.newSingleThreadScheduledExecutor();
        
        TaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);

        scheduler.schedule(
            () -> {
                String message = "type=timeout&itemId=" + itemId;
                rabbitTemplate.convertAndSend(exchange, service, message);
            },
            Instant.now().plusSeconds(TIMEOUT_SECONDS));
    }
    
}

package com.derso.architecture.timeout;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    private static final String exchange = "architecture-studies";
    
    @Value("${timeout.seconds:300}")
    private int timeoutSeconds;
    
    @Value("${timeout.threadpool.howmany:4}")
    private int howManyThreads;
    
    private TaskScheduler scheduler;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public TimeoutTask() {
        System.out.println(
            "Criando o pool de threads do agendador de timeouts");
        
        ScheduledExecutorService localExecutor = 
            Executors.newScheduledThreadPool(howManyThreads);
            
        scheduler = new ConcurrentTaskScheduler(localExecutor);
    }
    
    @Async
    public void scheduleTimeouts(long itemId, String service) {
        scheduler.schedule(
            () -> {
                String message = "type=timeout&itemId=" + itemId;
                rabbitTemplate.convertAndSend(exchange, service, message);
            },
            Instant.now().plusSeconds(timeoutSeconds)
        );
    }
    
}

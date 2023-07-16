package com.derso.architecture.sagastest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/testes")
public class SagasTestController {
    
    static record TimeoutRequest(long itemId, String service) {
    }
    
    private RestTemplate rest = new RestTemplate();
    
    @Value("${timeout.rabbitmq-exchange}")
    private String exchangeName;
    
    @Value("${timeout.main-url}")
    private String timeoutUrl;
    
    @PostMapping("/{id}/pre-reserva")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void efetuarPreReserva(@PathVariable("id") long id) {
        HttpEntity<TimeoutRequest> requestBody =
            new HttpEntity<>(new TimeoutRequest(id, exchangeName));
        
        rest.postForEntity(
            timeoutUrl + "/schedule-timeout", 
            requestBody, 
            null // Seria a classe de retorno, mas aqui é 204
        );
    }

}

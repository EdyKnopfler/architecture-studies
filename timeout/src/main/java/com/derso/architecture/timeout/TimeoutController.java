package com.derso.architecture.timeout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeoutController {
    
    @Autowired
    private TimeoutTask timeoutTask;
    
    static record TimeoutRequest(long itemId, String service) {
    }
    
    /**
     * Disparado por cada serviço que realiza uma pré-reserva
     * @param timeouts {"itemId": <long>, "service": <string>}
     */
    @PostMapping("/schedule-timeout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void scheduleTimeout(@RequestBody TimeoutRequest timeouts) {
        timeoutTask.scheduleTimeouts(timeouts.itemId(), timeouts.service());
    }

}

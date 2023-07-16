package com.derso.architecture.sagastest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/testes")
public class SagasTestController {
    
    @PostMapping("/:id/pre-reserva")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void efetuarPreReserva(@PathParam("id") long id) {
        
    }

}

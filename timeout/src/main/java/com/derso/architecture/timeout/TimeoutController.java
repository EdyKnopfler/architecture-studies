package com.derso.architecture.timeout;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeoutController {
    
    static record TimeoutRequest(String ... services) {
    }
    
    @PostMapping("/schedule-timeout")
    public String[] scheduleTimeout(@RequestBody TimeoutRequest timeouts) {
        return timeouts.services();
    }

}

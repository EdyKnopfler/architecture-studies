package com.derso.disparotimeouts;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TimeoutExclusoes {
	
	@Scheduled(fixedDelayString = "${intervalo-exclusoes}")
	public void executarTimeouts() {
		try {
			System.out.println("Executando exclusões...");
			Thread.sleep(3000);
			System.out.println("Executei exclusões!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

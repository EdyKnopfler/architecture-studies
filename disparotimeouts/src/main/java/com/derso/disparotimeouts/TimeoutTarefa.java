package com.derso.disparotimeouts;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TimeoutTarefa {
	
	/*
	 * fixedRate = dispara impreterivelmente à taxa indicada
	 * fixedDelay = aguarda o tempo entre uma execução e outra
	 * 
	 * Não queremos que as execuções se sobreponham. Um pool de 2 threads foi alocado.
	 * 
	 * Ref.: https://medium.com/@ali.gelenler/deep-dive-into-spring-schedulers-and-async-methods-27b6586a5a17
	 */
	@Scheduled(fixedDelayString = "${intervalo-timeouts}")
	public void executarTimeouts() {
		try {
			System.out.println("Executando timeouts...");
			Thread.sleep(3000);
			System.out.println("Executei timeouts!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

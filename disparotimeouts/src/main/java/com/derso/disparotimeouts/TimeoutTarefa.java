package com.derso.disparotimeouts;

import java.time.ZoneId;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.derso.controlesessao.persistencia.Sessao;
import com.derso.controlesessao.persistencia.SessaoRepositorio;

@Service
public class TimeoutTarefa {
	
	private final String exchange = "architecture-studies";
	private final String[] servicos = {"hoteis", "voos"};
	
	@Autowired
	private SessaoRepositorio sessaoRepositorio;
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
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
		List<Sessao> sessoes = sessaoRepositorio.sessoesExpiradasCorrendo();
		
		for (Sessao sessao : sessoes) {
			System.out.println(
					"Sessão expirada em: " + sessao.getExpiracao().atZone(
							ZoneId.of("America/Sao_Paulo")));
		}
		
		/*
		for (String servico : servicos) {
			String mensagem = "type=timeout&itemId=XXXX";
			System.out.println("Enviando " + mensagem);
			rabbitTemplate.convertAndSend(exchange, servico, mensagem);
		}
		*/
	}

}

package com.derso.controlesessao.controlesessao.trava;

import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.stereotype.Service;

@Service
public class ServicoTrava {
	
	@Autowired
	private ExpirableLockRegistry lockRegistry;
	
	public boolean executarSobTrava(String idSessao, Runnable acao) {
		System.out.println("Vou obter a trava " + idSessao);
		Lock lock = lockRegistry.obtain(idSessao);
        boolean success = lock.tryLock();

        if (!success) {
            System.out.println("não obtive trava " + idSessao);
            return false;
        }
        
        System.out.println("obtive trava " + idSessao);
        acao.run();
        lock.unlock();
        return true;
	}

}

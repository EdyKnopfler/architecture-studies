package com.derso.controlesessao.controlesessao;

import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.stereotype.Service;

@Service
public class ServicoTrava {
	
	@Autowired
	private ExpirableLockRegistry lockRegistry;
	
	public boolean executarSobTrava(String idSessao, Runnable acao) {
		Lock lock = lockRegistry.obtain(idSessao);
        boolean success = lock.tryLock();

        if (!success) {
            return false;
        }
        
        acao.run();
        lock.unlock();
        return true;
	}

}

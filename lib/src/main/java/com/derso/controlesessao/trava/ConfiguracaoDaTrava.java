package com.derso.controlesessao.trava;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.redis.util.RedisLockRegistry.RedisLockType;
import org.springframework.integration.support.locks.ExpirableLockRegistry;

@Configuration
public class ConfiguracaoDaTrava {
	
	private static final String NOME_TRAVA = "SESSAO_MUTEX";
	private static final Duration DURACAO_TRAVA = Duration.ofSeconds(30);
	
	@Bean(destroyMethod = "destroy")
	public ExpirableLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
	    RedisLockRegistry registroTravas = 
	    		new RedisLockRegistry(redisConnectionFactory, NOME_TRAVA, DURACAO_TRAVA.toMillis());
	    registroTravas.setRedisLockType(RedisLockType.PUB_SUB_LOCK);
	    return registroTravas;
	}


}

package br.com.fluxmanager.chat.whatsapp.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSessionStore {

    private final ReactiveStringRedisTemplate redisTemplate;
    private static final String SESSION_KEY_PREFIX = "whatsapp:session:";

    // Salva as chaves de criptografia do whatsapp

    public Mono<Boolean> saveSessionKeys(UUID instanceId, String sessionData) {
        String key = SESSION_KEY_PREFIX + instanceId.toString();
        log.info("Persistindo chaves de sessão no Redis para a instancia {}", instanceId);
        return redisTemplate.opsForValue().set(key,sessionData);
    }

    // Recupera as chaves salvas para reconectar a sessão do whatsapp

    public Mono<String> getSessionKeys(UUID instanceId) {
        String key = SESSION_KEY_PREFIX + instanceId.toString();
        return redisTemplate.opsForValue().get(key);
    }

    // Remove as chaves quando o cliente clica em "Desconectar" ou quando a sessão expira

    public Mono<Boolean> deleteSessionSkeys(UUID instanceId) {
        String key = SESSION_KEY_PREFIX + instanceId.toString();
        log.info("Removendo chaves de sessão do Redis para a instancia {}", instanceId);
        return redisTemplate.opsForValue().delete(key);
    }
}
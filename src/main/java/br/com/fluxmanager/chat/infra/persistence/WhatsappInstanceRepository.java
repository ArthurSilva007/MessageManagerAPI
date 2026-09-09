package br.com.fluxmanager.chat.infra.persistence;

import br.com.fluxmanager.chat.core.domain.WhatsappInstance;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface WhatsappInstanceRepository extends ReactiveCrudRepository<WhatsappInstance, UUID> {
    // Retorna todos os numeros de whatsapp cadastrados para um determinado cliente
    Flux<WhatsappInstance> findByClientId(UUID clientId);

    // Validacao de seguranca: busca a instancia verificando se ela realmente pertence ao cliente que esta tentando acessa-la
    Mono<WhatsappInstance> findByIdAndClientId(UUID id, UUID clientId);
}

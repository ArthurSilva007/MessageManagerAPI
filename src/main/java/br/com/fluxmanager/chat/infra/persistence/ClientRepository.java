package br.com.fluxmanager.chat.infra.persistence;

import br.com.fluxmanager.chat.core.domain.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<Client, UUID> {

     // Mono a promessa de que o banco de dados vai retornar 0 ou 1 item (Buscar um cliente)
    Mono<Client>findByApiKey(String apiKey);
}

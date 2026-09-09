package br.com.fluxmanager.chat.infra.persistence;

import br.com.fluxmanager.chat.core.domain.Subscription;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, UUID> {

    Mono<Subscription> findByClientId(UUID clientId);
}

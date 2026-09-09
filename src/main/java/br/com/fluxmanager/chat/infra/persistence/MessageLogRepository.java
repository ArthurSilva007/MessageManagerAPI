package br.com.fluxmanager.chat.infra.persistence;

import br.com.fluxmanager.chat.core.domain.MessageLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Repository
public interface MessageLogRepository extends ReactiveCrudRepository<MessageLog, UUID> {

    // O Spring lê esse nome e monta sozinho: SELECT * FROM message_logs WHERE status = ?
    Flux<MessageLog> findByStatus(String status);
}

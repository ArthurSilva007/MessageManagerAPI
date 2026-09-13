package br.com.fluxmanager.chat.api.controllers;

import br.com.fluxmanager.chat.api.dtos.InstanceRespondeDTO;
import br.com.fluxmanager.chat.core.domain.WhatsappInstance;
import br.com.fluxmanager.chat.infra.persistence.WhatsappInstanceRepository;
import br.com.fluxmanager.chat.whatsapp.manager.WhatsappConnectionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/instances")
@RequiredArgsConstructor
public class InstanceController {

    private final WhatsappInstanceRepository instanceRepository;
    private final WhatsappConnectionManager connectionManager;

    // crie uma nova instancia

    @PostMapping
    public Mono<ResponseEntity<InstanceRespondeDTO>> createInstance(
            @RequestHeader("X-client-ID") UUID clientId,
            @RequestParam("instanceName") String instanceName) {

        WhatsappInstance newInstance = WhatsappInstance.builder()
                .id(UUID.randomUUID())
                .clientId(clientId)
                .instanceName(instanceName)
                .connectionStatus("INITIALIZING")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return instanceRepository.save(newInstance)
                .doOnSuccess(saved -> connectionManager.startConnection(clientId, saved.getId()))
                .map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved)));
    }

    private InstanceRespondeDTO toDTO(WhatsappInstance instance) {
        return InstanceRespondeDTO.builder()
                .id(instance.getId())
                .instanceName(instance.getInstanceName())
                .phoneNumber(instance.getPhoneNumber())
                .connectionStatus(instance.getConnectionStatus())
                .createdAt(instance.getCreatedAt())
                .build();
    }
    @GetMapping
    public Flux<InstanceRespondeDTO> listInstances(@RequestHeader("X-client-ID") UUID clientId) {
        return instanceRepository.findByClientId(clientId)
                .map(this::toDTO);
    }
    @DeleteMapping("/{id}/disconnect")
    public Mono<ResponseEntity<Void>> disconnectInstance(
            @RequestHeader("X-client-ID") UUID clientId,
            @PathVariable UUID id){
        return instanceRepository.findByIdAndClientId(id,clientId)
                .flatMap(instance -> {
                connectionManager.disconnect(id);
                instance.setConnectionStatus("DISCONNECTED");
                instance.setUpdatedAt(LocalDateTime.now());
                return instanceRepository.save(instance);
                })
                .map(update -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}


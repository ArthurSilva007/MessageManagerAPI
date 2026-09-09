package br.com.fluxmanager.chat.whatsapp.listeners;

import br.com.fluxmanager.chat.infra.persistence.WhatsappInstanceRepository;
import it.auties.whatsapp.api.DisconnectReason;
import it.auties.whatsapp.listener.Listener;
import it.auties.whatsapp.model.info.MessageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class WhatsappEventListener implements Listener {

    private final WhatsappInstanceRepository instanceRepository;
    private final UUID instanceId;

    // Disparado quando a conexão é estabelecida com sucesso

    @Override
    public void onLoggedIn() {
        log.info ("Instancia {} autenticada e conectada com sucesso", instanceId);

        instanceRepository.findById(instanceId)
                .flatMap(instance -> {
                    instance.setConnectionStatus("CONNECTED");
                    instance.setUpdatedAt(LocalDateTime.now());
                    return instanceRepository.save(instance);
                })
                .subscribe();
    }

    @Override
    public void onDisconnected(DisconnectReason reason) {
        log.info("Instancia {} desconectada: {}", instanceId, reason);

        instanceRepository.findById(instanceId)
                .flatMap(instance -> {
                    instance.setConnectionStatus("DISCONNECTED");
                    instance.setUpdatedAt(LocalDateTime.now());
                    return instanceRepository.save(instance);
                })
                .subscribe();
    }

    @Override
    public void onNewMessage(MessageInfo info) {
        log.info("Nova mensagem recebida na instancia {}; De={}", instanceId, info.senderJid());
    }
}

package br.com.fluxmanager.chat.whatsapp.manager;

import br.com.fluxmanager.chat.infra.persistence.WhatsappInstanceRepository;
import br.com.fluxmanager.chat.whatsapp.listeners.WhatsappEventListener;
import it.auties.whatsapp.api.Whatsapp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class WhatsappConnectionManager {

    private final WhatsappInstanceRepository instanceRepository;

    // Um mapa em memoria para guardar as sessões ativas e podermos usar de forma eficiente
    private final Map<UUID, Whatsapp> activeConnections = new ConcurrentHashMap<>();

    public void startConnection(UUID clientId, UUID instanceId) {
        log.info("Iniciando conexão para o cliente {} com a instância {}", clientId, instanceId);

        // constroi a api do whatsapp (versao web/mobile) e inicia a conexão
        Whatsapp whatsapp = Whatsapp.webBuilder()
                .lastConnection()
                //aqui interceptamos a conexão para poder salvar o status da conexão no banco de dados
                .unregistered(qrCode -> {
                    log.info("QR code gerado para instancia {}. Aguardando leitura...", instanceId);
                    saveQrCodeState(instanceId, qrCode);
                });

        // adicionando os nossos ouvintes
        whatsapp.addListener(new WhatsappEventListener(instanceRepository, instanceId));

        // Guarda a conexão ativa no mapa
        activeConnections.put(instanceId, whatsapp);

        // conecta assincronamente (não trava o sistema)
        whatsapp.connect();
    }

    public Whatsapp getConnection(UUID instanceId) {
        return activeConnections.get(instanceId);
    }

    public void disconnect(UUID instanceId) {
        Whatsapp whatsapp = activeConnections.remove(instanceId);
        if (whatsapp != null) {
            whatsapp.disconnect();
            log.info("Conexão com a instância {} desconectada.", instanceId);
        }
    }

    private void saveQrCodeState(UUID instanceId, String qrCode) {
        //Atualiza o banco de dados informando que a instancia esta waiting_qr
        instanceRepository.findById(instanceId).subscribe(instance -> {
            instance.setConnectionStatus("WAITING_QR");
            instanceRepository.save(instance).subscribe();
        });
    }
}

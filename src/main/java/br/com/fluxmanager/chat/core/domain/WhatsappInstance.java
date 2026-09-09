package br.com.fluxmanager.chat.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "whatsapp_instances")
public class WhatsappInstance {

    @Id
    private UUID id;

    private UUID clientId;
    private String instanceName;
    private String phoneNumber;
    private String connectionStatus;  // e.g., "CONNECTED", "DISCONNECTED"
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
}

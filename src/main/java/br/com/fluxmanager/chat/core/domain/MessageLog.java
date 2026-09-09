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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message_logs")
public class MessageLog {

    @Id
    private UUID id;

    private UUID clientId;
    private UUID instanceId;
    private String remoteJId;
    private String messageType;
    private String content;
    private String mediaUrl;
    private String status; // e.g., "SENT", "DELIVERED", "READ"
    private String errorMessage; // Optional field for error details
    private LocalDateTime createdAt;
}

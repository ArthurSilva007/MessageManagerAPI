package br.com.fluxmanager.chat.api.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class InstanceRespondeDTO {

    private UUID id;
    private String instanceName;
    private String phoneNumber;
    private String connectionStatus;
    private LocalDateTime createdAt;
}

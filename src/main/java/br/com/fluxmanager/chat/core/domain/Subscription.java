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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subscriptions")
public class Subscription {

    @Id
    private UUID id;

    private UUID clientId;
    private String planName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

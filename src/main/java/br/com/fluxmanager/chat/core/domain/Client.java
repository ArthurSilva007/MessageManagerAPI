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
@Table(name = "clients")
public class Client {

    @Id
    private UUID id;

    private String name;
    private String email;
    private String password;
    private String apikey;
    private String webhookUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

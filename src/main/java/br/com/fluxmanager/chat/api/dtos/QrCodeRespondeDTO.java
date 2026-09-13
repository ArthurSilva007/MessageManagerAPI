package br.com.fluxmanager.chat.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QrCodeRespondeDTO {

    private String instanceId;
    private String qrCodeBase64;
    private String status;
}

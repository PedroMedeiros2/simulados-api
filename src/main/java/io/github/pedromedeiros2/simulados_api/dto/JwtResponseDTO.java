package io.github.pedromedeiros2.simulados_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private UUID id;
    private String username;
    private String name;
    private String role;

    public JwtResponseDTO(String accessToken, UUID id, String username, String name, String role) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
    }
}

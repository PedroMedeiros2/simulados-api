package io.github.pedromedeiros2.simulados_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Email is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}

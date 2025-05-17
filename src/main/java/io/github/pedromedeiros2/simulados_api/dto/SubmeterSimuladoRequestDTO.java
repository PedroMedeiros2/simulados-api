package io.github.pedromedeiros2.simulados_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmeterSimuladoRequestDTO {

    @NotNull(message = "O ID do simulado é obrigatório")
    private UUID simuladoId;

    @NotNull(message = "As respostas não podem ser nulas")
    private Map<Long, String> respostas;
}

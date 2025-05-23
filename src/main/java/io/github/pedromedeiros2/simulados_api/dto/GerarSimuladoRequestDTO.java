package io.github.pedromedeiros2.simulados_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GerarSimuladoRequestDTO {
    @NotNull
    @Size(min = 1)
    private List<FiltroSimuladoDTO> filtros;

    private boolean aceitarMenosQuestoes = false;
}

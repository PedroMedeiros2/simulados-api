package io.github.pedromedeiros2.simulados_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimuladoResponseDTO {
    private UUID id;
    private UUID userId;
    private String username;
    private LocalDateTime dataRealizacao;
    private Double nota;
    private String disciplinaFiltro;
    private String nivelDificuldadeFiltro;
    private Integer numeroQuestoesSolicitado;
    private List<SimuladoQuestaoResponseDTO> questoes;
}
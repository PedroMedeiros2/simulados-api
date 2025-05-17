package io.github.pedromedeiros2.simulados_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimuladoQuestaoResponseDTO {
    private Long id;
    private UUID questionId;
    private String enunciado;
    private List<String> alternatives;
    private String respostaCorretaQuestao;
    private String respostaUsuario;
    private Boolean correta;
}

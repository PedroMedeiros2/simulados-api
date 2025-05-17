package io.github.pedromedeiros2.simulados_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimuladoQuestaoResponseDTO {
    private Long id;
    private Long questionId;
    private String enunciado;
    private List<String> alternatives;
    private String respostaCorretaQuestao;
    private String respostaUsuario;
    private Boolean correta;
}

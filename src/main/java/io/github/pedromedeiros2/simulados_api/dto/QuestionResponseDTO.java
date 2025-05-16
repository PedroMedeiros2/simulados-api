package io.github.pedromedeiros2.simulados_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDTO {
    private UUID id;
    private String enunciado;
    private List<String> alternatives;
    private String respostaCorreta;
    private String disciplina;
    private String nivelDificuldade;
}

package io.github.pedromedeiros2.simulados_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDTO {
    @NotBlank
    private String enunciado;

    @NotNull
    @Size(min = 2, max = 5, message = "Deve haver entre 2 e 5 alternativas")
    private List<String> alternatives;

    @NotBlank(message = "A resposta correta não pode ser nula ou vazia")
    private String respostaCorreta;

    @NotBlank
    private String disciplina;

    @NotBlank
    private String nivelDificuldade;

}

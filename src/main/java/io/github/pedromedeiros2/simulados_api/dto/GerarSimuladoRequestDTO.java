package io.github.pedromedeiros2.simulados_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GerarSimuladoRequestDTO {
    @NotNull(message = "O número de questões é obrigatório")
    @Min(value = 1, message = "O simulado deve ter no mínimo 1 questão")
    @Max(value = 100, message = "O simulado pode ter no máximo 100 questões")
    private Integer numeroQuestoes;

    private String disciplina;

    private String nivelDificuldade;
}

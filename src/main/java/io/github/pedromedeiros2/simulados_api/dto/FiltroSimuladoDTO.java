package io.github.pedromedeiros2.simulados_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroSimuladoDTO {
    @NotNull
    private String disciplina;

    @NotEmpty
    private List<String> dificuldades;

    @NotNull
    @Min(1)
    private Integer numeroQuestoes;
}

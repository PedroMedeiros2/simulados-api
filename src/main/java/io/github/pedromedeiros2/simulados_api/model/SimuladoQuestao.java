package io.github.pedromedeiros2.simulados_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "simulados_questoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimuladoQuestao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulado_id", nullable = false)
    private Simulado simulado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    private String respostaUsuario;

    private Boolean correta;

    public SimuladoQuestao(Simulado simulado, Question question) {
        this.simulado = simulado;
        this.question = question;
    }
}

package io.github.pedromedeiros2.simulados_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "simulados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Simulado {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime dataRealizacao;

    private Double nota;

    private String disciplinaFiltro;
    private String nivelDificuldadeFiltro;
    private Integer numeroQuestoesSolicitado;

    @OneToMany(mappedBy = "simulado", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SimuladoQuestao> questoesSimulado = new ArrayList<>();


    public void addQuestao(Question question, String respostaUsuario, Boolean correta) {
        SimuladoQuestao simuladoQuestao = new SimuladoQuestao(this, question);
        simuladoQuestao.setRespostaUsuario(respostaUsuario);
        simuladoQuestao.setCorreta(correta);
        this.questoesSimulado.add(simuladoQuestao);
    }
}

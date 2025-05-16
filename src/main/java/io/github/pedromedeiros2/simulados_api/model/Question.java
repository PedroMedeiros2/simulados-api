package io.github.pedromedeiros2.simulados_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String enunciado;

    @NotNull
    @Size(min = 2, max = 5)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "question_alternatives", joinColumns = @JoinColumn(name = "question_id"))
    @OrderColumn(name = "alternative_index")
    @Column(name = "alternative", columnDefinition = "TEXT")
    private List<String> alternatives;

    @NotBlank
    private String respostaCorreta;

    @NotBlank
    private String disciplina;

    @NotBlank
    private String nivelDificuldade;
}

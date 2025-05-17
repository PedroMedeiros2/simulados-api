package io.github.pedromedeiros2.simulados_api.repository;

import io.github.pedromedeiros2.simulados_api.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByDisciplinaAndNivelDificuldade(String disciplina, String nivelDificuldade);
    List<Question> findByDisciplina(String disciplina);
    List<Question> findByNivelDificuldade(String nivelDificuldade);
}

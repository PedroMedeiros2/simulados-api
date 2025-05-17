package io.github.pedromedeiros2.simulados_api.service;

import io.github.pedromedeiros2.simulados_api.dto.QuestionRequestDTO;
import io.github.pedromedeiros2.simulados_api.dto.QuestionResponseDTO;
import io.github.pedromedeiros2.simulados_api.model.Question;
import io.github.pedromedeiros2.simulados_api.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    public QuestionResponseDTO createQuestion(QuestionRequestDTO questionRequestDTO) {
        Question question = new Question();

        question.setEnunciado(questionRequestDTO.getEnunciado());
        question.setAlternatives(questionRequestDTO.getAlternatives());
        question.setRespostaCorreta(questionRequestDTO.getRespostaCorreta());
        question.setDisciplina(questionRequestDTO.getDisciplina());
        question.setNivelDificuldade(questionRequestDTO.getNivelDificuldade());

        Question savedQuestion = questionRepository.save(question);
        return mapToResponseDTO(savedQuestion);
    }

    @Transactional(readOnly = true)
    public Optional<QuestionResponseDTO> getQuestionById(UUID id) {
        return questionRepository.findById(id).map(this::mapToResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<QuestionResponseDTO> updateQuestion(UUID id, QuestionRequestDTO questionRequestDTO) {
        Optional<Question> existingQuestionOptional = questionRepository.findById(id);
        if (existingQuestionOptional.isPresent()) {
            Question existingQuestion = existingQuestionOptional.get();
            existingQuestion.setEnunciado(questionRequestDTO.getEnunciado());
            existingQuestion.setAlternatives(questionRequestDTO.getAlternatives());
            existingQuestion.setRespostaCorreta(questionRequestDTO.getRespostaCorreta());
            existingQuestion.setDisciplina(questionRequestDTO.getDisciplina());
            existingQuestion.setNivelDificuldade(questionRequestDTO.getNivelDificuldade());

            Question updatedQuestion = questionRepository.save(existingQuestion);
            return Optional.of(mapToResponseDTO(updatedQuestion));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteQuestion(UUID id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private QuestionResponseDTO mapToResponseDTO(Question savedQuestion) {
        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();

        questionResponseDTO.setId(savedQuestion.getId());
        questionResponseDTO.setEnunciado(savedQuestion.getEnunciado());
        questionResponseDTO.setAlternatives(savedQuestion.getAlternatives());
        questionResponseDTO.setRespostaCorreta(savedQuestion.getRespostaCorreta());
        questionResponseDTO.setDisciplina(savedQuestion.getDisciplina());
        questionResponseDTO.setNivelDificuldade(savedQuestion.getNivelDificuldade());

        return questionResponseDTO;
    }
}


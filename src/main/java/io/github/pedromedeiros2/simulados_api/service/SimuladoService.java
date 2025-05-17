package io.github.pedromedeiros2.simulados_api.service;

import io.github.pedromedeiros2.simulados_api.dto.GerarSimuladoRequestDTO;
import io.github.pedromedeiros2.simulados_api.dto.SimuladoQuestaoResponseDTO;
import io.github.pedromedeiros2.simulados_api.dto.SimuladoResponseDTO;
import io.github.pedromedeiros2.simulados_api.dto.SubmeterSimuladoRequestDTO;
import io.github.pedromedeiros2.simulados_api.model.Question;
import io.github.pedromedeiros2.simulados_api.model.Simulado;
import io.github.pedromedeiros2.simulados_api.model.SimuladoQuestao;
import io.github.pedromedeiros2.simulados_api.model.User;
import io.github.pedromedeiros2.simulados_api.repository.QuestionRepository;
import io.github.pedromedeiros2.simulados_api.repository.SimuladoRepository;
import io.github.pedromedeiros2.simulados_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SimuladoService {
    @Autowired
    private SimuladoRepository simuladoRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public SimuladoResponseDTO gerarSimulado(GerarSimuladoRequestDTO requestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + currentUsername));

        List<Question> availableQuestions;
        if (requestDTO.getDisciplina() != null && !requestDTO.getDisciplina().isBlank() &&
                requestDTO.getNivelDificuldade() != null && !requestDTO.getNivelDificuldade().isBlank()) {
            availableQuestions = questionRepository.findByDisciplinaAndNivelDificuldade(requestDTO.getDisciplina(), requestDTO.getNivelDificuldade());
        } else if (requestDTO.getDisciplina() != null && !requestDTO.getDisciplina().isBlank()) {
            availableQuestions = questionRepository.findByDisciplina(requestDTO.getDisciplina());
        } else if (requestDTO.getNivelDificuldade() != null && !requestDTO.getNivelDificuldade().isBlank()) {
            availableQuestions = questionRepository.findByNivelDificuldade(requestDTO.getNivelDificuldade());
        } else {
            availableQuestions = questionRepository.findAll();
        }

        if (availableQuestions.isEmpty()) {
            throw new RuntimeException("Nenhuma questão encontrada para os critérios fornecidos.");
        }

        Collections.shuffle(availableQuestions);
        List<Question> selectedQuestions = availableQuestions.stream()
                .limit(requestDTO.getNumeroQuestoes())
                .toList();

        if (selectedQuestions.size() < requestDTO.getNumeroQuestoes()) {
            if (!requestDTO.isAceitarMenosQuestoes()) {
                throw new RuntimeException("Apenas " + selectedQuestions.size() +
                        " questões disponíveis para os critérios fornecidos. Envie novamente com 'aceitarMenosQuestoes = true' para continuar com menos questões.");
            }
        }

        Simulado simulado = new Simulado();
        simulado.setUser(currentUser);
        simulado.setDataRealizacao(LocalDateTime.now());
        simulado.setDisciplinaFiltro(requestDTO.getDisciplina());
        simulado.setNivelDificuldadeFiltro(requestDTO.getNivelDificuldade());
        simulado.setNumeroQuestoesSolicitado(requestDTO.getNumeroQuestoes());

        selectedQuestions.forEach(q -> {
            SimuladoQuestao sq = new SimuladoQuestao(simulado, q);
            simulado.getQuestoesSimulado().add(sq);
        });

        Simulado savedSimulado = simuladoRepository.save(simulado);
        return mapToSimuladoResponseDTO(savedSimulado, false); // false: don't include answers yet
    }

    @Transactional
    public SimuladoResponseDTO submeterSimulado(SubmeterSimuladoRequestDTO requestDTO) {
        Simulado simulado = simuladoRepository.findById(requestDTO.getSimuladoId())
                .orElseThrow(() -> new RuntimeException("Simulado não encontrado com ID: " + requestDTO.getSimuladoId()));

        // Ensure the user submitting is the one who started it (optional, based on requirements)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        if (!simulado.getUser().getUsername().equals(currentUsername)) {
            throw new RuntimeException("Usuário não autorizado a submeter este simulado.");
        }

        if (simulado.getNota() != null) {
            throw new RuntimeException("Este simulado já foi submetido e corrigido.");
        }

        int acertos = 0;
        for (SimuladoQuestao sq : simulado.getQuestoesSimulado()) {
            String respostaUsuario = requestDTO.getRespostas().get(sq.getQuestion().getId());
            sq.setRespostaUsuario(respostaUsuario);
            if (respostaUsuario != null && respostaUsuario.equals(sq.getQuestion().getRespostaCorreta())) {
                sq.setCorreta(true);
                acertos++;
            } else {
                sq.setCorreta(false);
            }
        }

        double nota = (double) acertos / simulado.getQuestoesSimulado().size() * 100.0;
        simulado.setNota(nota);
        simulado.setDataRealizacao(LocalDateTime.now()); // Update realization time to submission time

        Simulado savedSimulado = simuladoRepository.save(simulado);
        return mapToSimuladoResponseDTO(savedSimulado, true); // true: include answers and results
    }

    @Transactional(readOnly = true)
    public Optional<SimuladoResponseDTO> getSimuladoById(UUID simuladoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + currentUsername));

        return simuladoRepository.findById(simuladoId)
                .filter(s -> s.getUser().getId().equals(currentUser.getId()) || "ADMIN".equals(currentUser.getRole())) // Allow user to see their own, or admin to see any
                .map(s -> mapToSimuladoResponseDTO(s, s.getNota() != null)); // Show answers if submitted
    }

    @Transactional(readOnly = true)
    public List<SimuladoResponseDTO> getHistoricoSimuladosUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + currentUsername));

        return simuladoRepository.findByUserId(currentUser.getId()).stream()
                .map(s -> mapToSimuladoResponseDTO(s, s.getNota() != null)) // Show answers if submitted
                .collect(Collectors.toList());
    }

    private SimuladoResponseDTO mapToSimuladoResponseDTO(Simulado simulado, boolean includeAnswers) {
        SimuladoResponseDTO dto = new SimuladoResponseDTO();
        dto.setId(simulado.getId());
        dto.setUserId(simulado.getUser().getId());
        dto.setUsername(simulado.getUser().getUsername());
        dto.setDataRealizacao(simulado.getDataRealizacao());
        dto.setNota(simulado.getNota());
        dto.setDisciplinaFiltro(simulado.getDisciplinaFiltro());
        dto.setNivelDificuldadeFiltro(simulado.getNivelDificuldadeFiltro());
        dto.setNumeroQuestoesSolicitado(simulado.getNumeroQuestoesSolicitado());

        List<SimuladoQuestaoResponseDTO> questoesDTO = simulado.getQuestoesSimulado().stream()
                .map(sq -> mapToSimuladoQuestaoResponseDTO(sq, includeAnswers))
                .collect(Collectors.toList());
        dto.setQuestoes(questoesDTO);
        return dto;
    }

    private SimuladoQuestaoResponseDTO mapToSimuladoQuestaoResponseDTO(SimuladoQuestao sq, boolean includeAnswers) {
        SimuladoQuestaoResponseDTO qDto = new SimuladoQuestaoResponseDTO();
        qDto.setId(sq.getId());
        qDto.setQuestionId(sq.getQuestion().getId());
        qDto.setEnunciado(sq.getQuestion().getEnunciado());
        qDto.setAlternatives(sq.getQuestion().getAlternatives());
        if (includeAnswers) {
            qDto.setRespostaCorretaQuestao(sq.getQuestion().getRespostaCorreta());
            qDto.setRespostaUsuario(sq.getRespostaUsuario());
            qDto.setCorreta(sq.getCorreta());
        }
        return qDto;
    }
}

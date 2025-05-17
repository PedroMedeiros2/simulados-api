package io.github.pedromedeiros2.simulados_api.controller;

import io.github.pedromedeiros2.simulados_api.dto.GerarSimuladoRequestDTO;
import io.github.pedromedeiros2.simulados_api.dto.SimuladoResponseDTO;
import io.github.pedromedeiros2.simulados_api.dto.SubmeterSimuladoRequestDTO;
import io.github.pedromedeiros2.simulados_api.service.SimuladoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/simulados")
public class SimuladoController {
    @Autowired
    private SimuladoService simuladoService;

    @PostMapping("/gerar")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> gerarSimulado(@Valid @RequestBody GerarSimuladoRequestDTO gerarSimuladoRequestDTO) {
        try {
            SimuladoResponseDTO simulado = simuladoService.gerarSimulado(gerarSimuladoRequestDTO);
            return ResponseEntity.ok(simulado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/submeter")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> submeterSimulado(@Valid @RequestBody SubmeterSimuladoRequestDTO submeterSimuladoRequestDTO) {
        try {
            SimuladoResponseDTO simuladoCorrigido = simuladoService.submeterSimulado(submeterSimuladoRequestDTO);
            return ResponseEntity.ok(simuladoCorrigido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SimuladoResponseDTO> getSimuladoById(@PathVariable UUID id) {
        Optional<SimuladoResponseDTO> simulado = simuladoService.getSimuladoById(id);
        return simulado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/meu-historico")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<SimuladoResponseDTO>> getMeuHistoricoSimulados() {
        List<SimuladoResponseDTO> historico = simuladoService.getHistoricoSimuladosUsuario();
        return ResponseEntity.ok(historico);
    }
}

package io.github.pedromedeiros2.simulados_api.repository;

import io.github.pedromedeiros2.simulados_api.model.Simulado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SimuladoRepository extends JpaRepository<Simulado, UUID> {
    List<Simulado> findByUserId(UUID userId);
}

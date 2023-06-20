package com.miccarv.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miccarv.project.model.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    Processo findByNumero(String numero);

    List<Processo> findByForo(String foro);

}

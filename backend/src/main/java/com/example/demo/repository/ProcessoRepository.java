package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    Processo findByNumero(String numero);
    List<Processo> findByForo(String foro);
  
}

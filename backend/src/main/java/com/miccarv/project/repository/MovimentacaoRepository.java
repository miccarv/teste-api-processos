package com.miccarv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miccarv.project.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

}

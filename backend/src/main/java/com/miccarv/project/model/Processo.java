package com.miccarv.project.model;

import java.util.List;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "processos")
public class Processo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "processo_id", nullable = false, unique = true)
	private Long id;
	@NaturalId
	private String numero;
	@Column(name = "partes_principais", length = 5000)
	private String partesPrincipais;
	private String area;
	private String vara;
	private String juiz;
	private String assunto;
	private String valor;
	private String foro;
	@Column(name = "data_distribuicao")
	private String dataDistribuicao;
	@JsonManagedReference
	@OneToMany(mappedBy = "processo", cascade = CascadeType.PERSIST)
	private List<Movimentacao> movimentacoes;

}
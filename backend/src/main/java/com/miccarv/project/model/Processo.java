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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "processos")
public class Processo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "processo_id", nullable = false, unique = true)
	private Long id;
	@NaturalId
	@Column(name = "numero")
	private String numero;
	@Column(name = "partes_principais", length = 5000)
	private String partesPrincipais;
	@Column(name = "area")
	private String area;
	@Column(name = "vara")
	private String vara;
	@Column(name = "juiz")
	private String juiz;
	@Column(name = "assunto")
	private String assunto;
	@Column(name = "valor")
	private String valor;
	@Column(name = "foro")
	private String foro;
	@Column(name = "data_distribuicao")
	private String dataDistribuicao;
	@JsonManagedReference
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL)
	private List<Movimentacao> movimentacoes;

}
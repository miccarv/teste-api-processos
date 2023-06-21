package com.miccarv.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "movimentacoes")
public class Movimentacao {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cnjnumber_fk", referencedColumnName = "numero")
	private Processo processo;
	private String data;
	@Column(length = 5000)
	private String descricao;

	public Movimentacao(String data, String descricao, Processo processo) {
		this.data = data;
		this.descricao = descricao;
		this.processo = processo;
	}

}


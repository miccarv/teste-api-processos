package com.example.demo.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "processos")
public class Item {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "processo_id", nullable = false, unique = true)
	private Long id;
	@Column(name = "cnjnumber")
	private String cnjNumber;
	private String partes;
	@Column(name = "tribunalorigem")
	private String tribunalOrigem;
	@Column(name = "data")
	private String data;
	@OneToMany(mappedBy = "item")
	private Set<Movimentacoes> movimentacoes;

	public Item() {
	}

	public Item(String cnjNumber, String partes, String tribunalOrigem, String data, Set<Movimentacoes> movimentacoes) {
		this.cnjNumber = cnjNumber;
		this.partes = partes;
		this.tribunalOrigem = tribunalOrigem;
		this.data = data;
		this.movimentacoes = movimentacoes;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCnjNumber() {
		return cnjNumber;
	}


	public void setCnjNumber(String cnjNumber) {
		this.cnjNumber = cnjNumber;
	}


	public String getPartes() {
		return partes;
	}


	public void setPartes(String partes) {
		this.partes = partes;
	}


	public String getTribunalOrigem() {
		return tribunalOrigem;
	}


	public void setTribunalOrigem(String tribunalOrigem) {
		this.tribunalOrigem = tribunalOrigem;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public Set<Movimentacoes> getMovimentacoes() {
		return movimentacoes;
	}


	public void setMovimentacoes(Set<Movimentacoes> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}
	
}
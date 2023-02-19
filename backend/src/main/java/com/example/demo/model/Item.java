package com.example.demo.model;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

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
	@NaturalId
	@Column(name = "cnjnumber")
	private String cnjNumber;
	@Column(name = "partes_principais")
	private String partes;
	@Column(name = "todas_partes", length = 5000)
	private String todasPartes;
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
	@Column(name = "tribunal_origem")
	private String tribunalOrigem;
	@Column(name = "data")
	private String data;
	@OneToMany(mappedBy = "item")
	private Set<Movimentacoes> movimentacoes;

	public Item() {
	}

	public Item(String cnjNumber, String partes, String todasPartes, String area, String vara, String juiz,
			String assunto, String valor, String tribunalOrigem, String data, Set<Movimentacoes> movimentacoes) {
		this.cnjNumber = cnjNumber;
		this.partes = partes;
		this.todasPartes = todasPartes;
		this.area = area;
		this.vara = vara;
		this.juiz = juiz;
		this.assunto = assunto;
		this.valor = valor;
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

	public String getTodasPartes() {
		return todasPartes;
	}

	public void setTodasPartes(String todasPartes) {
		this.todasPartes = todasPartes;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getVara() {
		return vara;
	}

	public void setVara(String vara) {
		this.vara = vara;
	}

	public String getJuiz() {
		return juiz;
	}

	public void setJuiz(String juiz) {
		this.juiz = juiz;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
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
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
	@Column(name = "numero")
	private String numero;
	@Column(name = "partes_principais")
	private String partesPrincipais;
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
	@Column(name = "foro")
	private String foro;
	@Column(name = "data_distribuicao")
	private String dataDistribuicao;
	@OneToMany(mappedBy = "item")
	private Set<Movimentacoes> movimentacoes;

	public Item() {
	}

	public Item(String numero, String partesPrincipais, String todasPartes, String area, String vara, String juiz, String assunto,
			String valor, String foro, String dataDistribuicao, Set<Movimentacoes> movimentacoes) {
		this.numero = numero;
		this.partesPrincipais = partesPrincipais;
		this.todasPartes = todasPartes;
		this.area = area;
		this.vara = vara;
		this.juiz = juiz;
		this.assunto = assunto;
		this.valor = valor;
		this.foro = foro;
		this.dataDistribuicao = dataDistribuicao;
		this.movimentacoes = movimentacoes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPartesPrincipais() {
		return partesPrincipais;
	}

	public void setPartesPrincipais(String partesPrincipais) {
		this.partesPrincipais = partesPrincipais;
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

	public String getForo() {
		return foro;
	}

	public void setForo(String foro) {
		this.foro = foro;
	}

	public String getDataDistribuicao() {
		return dataDistribuicao;
	}

	public void setDataDistribuicao(String dataDistribuicao) {
		this.dataDistribuicao = dataDistribuicao;
	}

	public Set<Movimentacoes> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(Set<Movimentacoes> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

}
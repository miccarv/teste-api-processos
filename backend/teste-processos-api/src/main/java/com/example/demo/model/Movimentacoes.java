package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="movimentacoes")
public class Movimentacoes {
    @Id
	@ManyToOne
    @JoinColumn(name="processo_id_fk", nullable=false, unique = false)
    private Item item;

	private String data;
	@Column(length = 500)
	private String description;

	public Movimentacoes(String data, String description) {
		this.data = data;
		this.description = description;
	}

	public Movimentacoes () {
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

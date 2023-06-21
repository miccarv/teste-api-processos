package com.miccarv.project.infra.elasticsearch.model;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;

import com.miccarv.project.model.Movimentacao;

import jakarta.persistence.Id;

@Document(indexName = "processos", createIndex = false)
public class ProcessoDoc {
    @Id
    private String id;
    private String numero;
    private String partesPrincipais;
    private String area;
    private String vara;
    private String juiz;
    private String assunto;
    private String valor;
    private String foro;
    private String dataDistribuicao;
    private List<Movimentacao> movimentacoes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
}

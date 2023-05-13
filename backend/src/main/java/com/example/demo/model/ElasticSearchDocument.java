package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@Document(indexName = "processos")
@NoArgsConstructor
public class ElasticSearchDocument {
    @Id
    private String id;
    private String numero;
    private String partesPrincipais;
    private String todasPartes;
    private String area;
    private String vara;
    private String juiz;
    private String assunto;
    private String valor;
    private String foro;
    private String dataDistribuicao;
    private Movimentacao[] _movimentacoes;

    public ElasticSearchDocument (Processo processo) {
        this.id = processo.getId().toString();
        this.numero = processo.getNumero();
        this.partesPrincipais = processo.getPartesPrincipais();
        this.todasPartes = processo.getTodasPartes();
        this.area = processo.getArea();
        this.vara = processo.getVara();
        this.juiz = processo.getJuiz();
        this.assunto = processo.getAssunto();
        this.valor = processo.getValor();
        this.foro = processo.getForo();
        this.dataDistribuicao = processo.getDataDistribuicao();
        //convert Movimentacoes list to array
        this._movimentacoes = processo.getMovimentacoes().toArray(new Movimentacao[processo.getMovimentacoes().size()]);
    }
    
}
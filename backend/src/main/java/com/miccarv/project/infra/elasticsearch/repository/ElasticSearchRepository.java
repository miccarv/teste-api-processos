package com.miccarv.project.infra.elasticsearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.miccarv.project.infra.elasticsearch.model.ProcessoDoc;

public interface ElasticSearchRepository extends ElasticsearchRepository<ProcessoDoc, String> {

    List<ProcessoDoc> findByForo(String foro);

    List<ProcessoDoc> findByNumero(String numero);
}
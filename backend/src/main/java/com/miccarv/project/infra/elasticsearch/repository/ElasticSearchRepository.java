package com.miccarv.project.infra.elasticsearch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.miccarv.project.infra.elasticsearch.model.ProcessoDoc;
import com.miccarv.project.model.Processo;

@Repository
public interface ElasticSearchRepository extends ElasticsearchRepository<ProcessoDoc, String> {
    List<Processo> findByForo(String foro);

    Optional<Processo> findByNumero(String numero);
}
package com.miccarv.project.infra.elasticsearch.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.miccarv.project.infra.elasticsearch.model.ProcessoDoc;
import com.miccarv.project.infra.elasticsearch.repository.ElasticSearchRepository;
import com.miccarv.project.infra.elasticsearch.service.ProcessoService;

@Service
public class DefaultProcessoService implements ProcessoService {

    private final ElasticSearchRepository elasticSearchRepository;

    public DefaultProcessoService(ElasticSearchRepository elasticSearchRepository) {
        this.elasticSearchRepository = elasticSearchRepository;
    }

    @Override
    public List<ProcessoDoc> getAll() {
        List<ProcessoDoc> processos = new ArrayList<>();
        elasticSearchRepository.findAll()
                .forEach(processos::add);
        return processos;
    }

    @Override
    public List<ProcessoDoc> findByAuthor(String foro) {
        throw new UnsupportedOperationException("Unimplemented method 'findByAuthor'");
    }

    @Override
    public List<ProcessoDoc> findByNumero(String numero) {
        throw new UnsupportedOperationException("Unimplemented method 'findByNumero'");
    }

    @Override
    public ProcessoDoc create(ProcessoDoc processo) throws IOException {
        return elasticSearchRepository.save(processo);
    }

}

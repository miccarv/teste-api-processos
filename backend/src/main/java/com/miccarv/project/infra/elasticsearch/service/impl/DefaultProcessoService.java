package com.miccarv.project.infra.elasticsearch.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.miccarv.project.infra.elasticsearch.model.ProcessoDoc;
import com.miccarv.project.infra.elasticsearch.repository.ElasticSearchRepository;
import com.miccarv.project.infra.elasticsearch.service.ESProcessoService;

@Service
public class DefaultProcessoService implements ESProcessoService {

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
    public List<ProcessoDoc> findByForo(String foro) {
        return elasticSearchRepository.findByForo(foro);
    }

    @Override
    public List<ProcessoDoc> findByNumero(String numero) {
        return elasticSearchRepository.findByNumero(numero);
    }

    @Override
    public ProcessoDoc create(ProcessoDoc processo) throws IOException {
        List<ProcessoDoc> matchedProcesso = findByNumero(processo.getNumero());
        if (matchedProcesso.size() >= 1) {
            processo.setId(matchedProcesso.get(0).getId());
        }
        return elasticSearchRepository.save(processo);
    }

}

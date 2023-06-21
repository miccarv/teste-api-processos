package com.miccarv.project.infra.elasticsearch.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.miccarv.project.infra.elasticsearch.model.ProcessoDoc;

@Service
public interface ProcessoService {

    List<ProcessoDoc> getAll();

    List<ProcessoDoc> findByForo(String foro);

    List<ProcessoDoc> findByNumero(String numero);

    ProcessoDoc create(ProcessoDoc processo) throws IOException;

}

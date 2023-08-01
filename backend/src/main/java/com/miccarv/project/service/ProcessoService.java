package com.miccarv.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.miccarv.project.model.Processo;
import com.miccarv.project.repository.ProcessoRepository;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;
    
    public List<Processo> getProcessos() {
        return processoRepository.findAll();
    }

    public void create(@RequestBody Processo processo) {
        List<Processo> matchedProcessos = processoRepository.findByNumero(processo.getNumero());
        if (matchedProcessos.size() >= 1)
            processo.setId(matchedProcessos.get(0).getId());
        processoRepository.save(processo);
    }

    public List<Processo> findByForo(@RequestParam(value = "foro") String foro)
            throws ResponseStatusException {
        List<Processo> processosByForo = processoRepository.findByForo(foro);
        if (!processosByForo.isEmpty())
            return processosByForo;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum resultado encontrado");
    }

    public List<Processo> findByCnj(@RequestParam(value = "cnj") String numero)
            throws ResponseStatusException {
        List<Processo> processoByCnj = processoRepository.findByNumero(numero);
        if (processoByCnj != null)
            return processoByCnj;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum resultado encontrado");
    }
}

package com.miccarv.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.miccarv.project.model.Processo;
import com.miccarv.project.repository.ProcessoRepository;

@RestController
@RequestMapping("/h2")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProcessoController {

    @Autowired
    private ProcessoRepository processoRepository;

    @GetMapping("/processos")
    public List<Processo> getProcessos() {
        return processoRepository.findAll();
    }

    @PostMapping("/processos")
    public void addProcessoToDatabase(@RequestBody Processo processo) {
        Processo matchedProcesso = processoRepository.findByNumero(processo.getNumero());
        if (matchedProcesso != null)
            processo.setId(matchedProcesso.getId());
        processoRepository.save(processo);
    }

    @GetMapping("/search-foro")
    public List<Processo> searchByForo(@RequestParam(value = "foro") String foro)
            throws ResponseStatusException {
        List<Processo> processosByForo = processoRepository.findByForo(foro);
        if (!processosByForo.isEmpty())
            return processosByForo;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum resultado encontrado");
    }

    @GetMapping("/search-cnj")
    public Processo searchByCnj(@RequestParam(value = "cnj") String numero)
            throws ResponseStatusException {
        Processo processoByCnj = processoRepository.findByNumero(numero);
        if (processoByCnj != null)
            return processoByCnj;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum resultado encontrado");
    }

}

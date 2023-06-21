package com.miccarv.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.miccarv.project.model.Processo;
import com.miccarv.project.service.ProcessoService;

@RestController
@RequestMapping("/h2")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @GetMapping("/processos")
    public List<Processo> getProcessos() {
        return processoService.getProcessos();
    }

    @PostMapping("/processos")
    public void addProcessoToDatabase(@RequestBody Processo processo) {
        processoService.create(processo);
    }

    @GetMapping("/search-foro")
    public List<Processo> searchByForo(@RequestParam(value = "foro") String foro)
            throws ResponseStatusException {
        return processoService.findByForo(foro);
    }

    @GetMapping("/search-cnj")
    public List<Processo> searchByCnj(@RequestParam(value = "cnj") String numero)
            throws ResponseStatusException {
        return processoService.findByCnj(numero);
    }

}

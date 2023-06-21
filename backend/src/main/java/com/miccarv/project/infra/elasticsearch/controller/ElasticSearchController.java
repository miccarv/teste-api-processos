package com.miccarv.project.infra.elasticsearch.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.miccarv.project.infra.elasticsearch.model.ProcessoDoc;
import com.miccarv.project.infra.elasticsearch.service.ProcessoService;

@RestController
@RequestMapping("/es")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ElasticSearchController {

	private final ProcessoService processoService;

	public ElasticSearchController(ProcessoService processoService) {
		this.processoService = processoService;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/processos")
	public Iterable<ProcessoDoc> getProcessos() throws IOException {
		return processoService.getAll();
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/processos")
	public ProcessoDoc createProcesso(@RequestBody ProcessoDoc processo) throws IOException {
		return processoService.create(processo);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/search-cnj")
	public List<ProcessoDoc> getProcessoByNumero(@RequestParam(value = "cnj") String numero) {
		return processoService.findByNumero(numero);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/search-foro")
	public List<ProcessoDoc> getProcessoByForo(@RequestParam(value = "foro") String foro) {
		return processoService.findByForo(foro);
	}

}

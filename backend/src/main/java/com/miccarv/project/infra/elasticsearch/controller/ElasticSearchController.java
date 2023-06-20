package com.miccarv.project.infra.elasticsearch.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		ProcessoDoc processo = new ProcessoDoc();
		processoService.create(processo);
		return processoService.getAll();
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/processos")
	public ProcessoDoc createProcesso(@RequestBody ProcessoDoc processo) throws IOException {
		return processoService.create(processo);
	}

}

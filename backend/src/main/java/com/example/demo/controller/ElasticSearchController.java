package com.example.demo.controller;

import java.io.IOException;
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

import com.example.demo.model.ElasticSearchDocument;
import com.example.demo.model.Processo;
import com.example.demo.repository.ElasticSearchRepository;

@RestController
@RequestMapping("/es")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ElasticSearchController {

	@Autowired
	private ElasticSearchRepository elasticSearchRepository;

	@GetMapping("/processos")
	public List<ElasticSearchDocument> getProcessos() throws IOException {
		return elasticSearchRepository.searchAllDocuments();
	}

	@PostMapping("/add-processo")
	public void addProcessoToDatabase(@RequestBody Processo processo) throws IOException {
		elasticSearchRepository.createOrUpdateDocument(processo);
	}

	@GetMapping("/search-cnj")
	public List<ElasticSearchDocument> searchByCnj(
			@RequestParam(value = "processoCnj") String processoCnj) throws IOException {
		List<ElasticSearchDocument> documents = elasticSearchRepository.getDocumentByNumero(processoCnj);
		if (!documents.isEmpty())
			return documents;
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum resultado encontrado");
	}

	@GetMapping("/search-foro")
	public List<ElasticSearchDocument> searchByForo(@RequestParam(value = "processoForo") String processoForo)
			throws IOException {
		List<ElasticSearchDocument> documents = elasticSearchRepository.getDocumentByForo(processoForo.toLowerCase());
		if (!documents.isEmpty())
			return documents;
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum resultado encontrado");
	}

}

package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Item;
import com.example.demo.repository.ProcessoRepository;

@RestController
@RequestMapping("/api")

// Faz a liberação do CORS para permitir que esta API seja acessada por outro
// servidor(React).

@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProcessosController {

	@Autowired
	private ProcessoRepository repository;
	
	// Método Get para retornar uma lista de processos.
	
	@GetMapping("/items")
	public List<Item> getItems() {		
		return repository.findAll();

	}

	// Método Get para comparar dados fornecidos pelo usuário com dados do bd e
	// retornar resultados obtidos.

	@GetMapping("/search")
	public List<Item> searchById(@RequestParam(value = "processoTr", required = false) String processoTr,
			@RequestParam(value = "processoCnj", required = false) String processoCnj) {
		List<Item> items = getItems();
		List<Item> searchById = new ArrayList<>();

		for (Item item : items) {
			if (item.getCnjNumber().equals(processoCnj) || item.getTribunalOrigem().equals(processoTr)) {
				searchById.add(item);
			}
		}

		// Lida com a excessão de resultado não encontrado.

		if (searchById.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum resultado encontrado");
		} else {
			return searchById;
		}
	}

}
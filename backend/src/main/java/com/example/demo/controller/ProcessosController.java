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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Item;
import com.example.demo.repository.ProcessoRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@RestController
@RequestMapping("/api")

// Faz a liberação do CORS para permitir que esta API seja acessada por outro
// servidor.
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProcessosController {

	private static final String DB_URL = "jdbc:h2:mem:processos";
	private static final String DB_USER = "sa";
	private static final String DB_PASSWORD = "password";

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
	public List<Item> searchById(@RequestParam(value = "processoCnj", required = false) String processoCnj) {

		List<Item> items = getItems();
		List<Item> searchById = new ArrayList<>();

		if (processoCnj != null) {
			boolean found = false;
			for (Item item : items) {
				if (item.getNumero().equals(processoCnj)) {
					searchById.add(item);
					found = true;
					break;
				}
			}

			if (!found) {
				try {
					// Receber do crawler a string com comando SQL
					String url = "http://crawler:8081/processos/" + processoCnj;
					RestTemplate restTemplate = new RestTemplate();
					String sqlStatement = restTemplate.getForObject(url, String.class);

					Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

					// Preparar comando SQL para adicionar item
					PreparedStatement stmt = conn.prepareStatement(sqlStatement);
					int numRowsAffected = stmt.executeUpdate();

					// Fechar comando e conexão
					stmt.close();
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
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

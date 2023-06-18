package com.example.demo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.demo.model.ElasticSearchDocument;
import com.example.demo.model.Processo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@CrossOrigin(origins = "*")
@Repository
public class ElasticSearchRepository {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private static final String INDEX_NAME = "processos";

    public String createOrUpdateDocument(Processo processo) throws IOException {
        boolean indexExists = elasticsearchClient.indices().exists(e -> e.index(INDEX_NAME)).value();
        if (!indexExists) {
            elasticsearchClient.indices().create(i -> i.index(INDEX_NAME));
        }

        processo.setId((long) 1);
        ElasticSearchDocument document = new ElasticSearchDocument(processo);
        List<ElasticSearchDocument> documentByNumero = getDocumentByNumero(document.getNumero());

        if (documentByNumero.isEmpty())
            document.setId(String.valueOf(searchAllDocuments().size() + 1));

        IndexResponse response = elasticsearchClient.index(i -> i
                .index(INDEX_NAME)
                .id(document.getId()).document(document));

        if (response.result().name().equals("Created")) {
            return new StringBuilder("Document has been successfully created.").toString();
        } else if (response.result().name().equals("Updated")) {
            return new StringBuilder("Document has been successfully updated.").toString();
        }

        return new StringBuilder("Error while performing the operation.").toString();

    }

    public ElasticSearchDocument getDocumentById(String itemId) throws IOException {
        ElasticSearchDocument document = null;
        GetResponse<ElasticSearchDocument> response = elasticsearchClient.get(g -> g
                .index(INDEX_NAME)
                .id(itemId), ElasticSearchDocument.class);

        if (response.found()) {
            document = response.source();
            System.out.println("Document found:  " + document.toString());
        } else {
            System.out.println("Document not found");
        }
        return document;
    }

    public String deleteDocumentById(String itemId) throws IOException {
        DeleteRequest request = DeleteRequest.of(d -> d.index(INDEX_NAME).id(itemId));
        DeleteResponse deleteResponse = elasticsearchClient.delete(request);

        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Document with id " + deleteResponse.id() + " has been deleted.").toString();
        }
        System.out.println("Document not found");
        return new StringBuilder("Document with id " + deleteResponse.id() + " does not exist.").toString();
    }

    public List<ElasticSearchDocument> searchAllDocuments() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s.index(INDEX_NAME));
        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, ElasticSearchDocument.class);
        List<Hit> hits = searchResponse.hits().hits();
        List<ElasticSearchDocument> documents = new ArrayList<>();

        for (Hit object : hits) {
            documents.add((ElasticSearchDocument) object.source());
        }
        return documents;
    }

    public List<ElasticSearchDocument> getDocumentByNumero(String numero) throws IOException {
        List<ElasticSearchDocument> documentsByNumero = new ArrayList<>();
        List<ElasticSearchDocument> documents = searchAllDocuments();

        for (ElasticSearchDocument document : documents) {
            if (document.getNumero().equals(numero)) {
                documentsByNumero.add(document);
            }
        }
        return documentsByNumero;
    }

    public List<ElasticSearchDocument> getDocumentByForo(String foro) throws IOException {
        List<ElasticSearchDocument> documentsByForo = new ArrayList<>();
        List<ElasticSearchDocument> documents = searchAllDocuments();

        for (ElasticSearchDocument document : documents) {
            if (document.getForo().equals(foro)) {
                documentsByForo.add(document);
            }
        }
        return documentsByForo;
    }

}
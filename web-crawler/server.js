const express = require("express");
const rp = require("request-promise");
const cheerio = require("cheerio");
const { Client } = require("elasticsearch");
const PORT = 8081;
const cors = require("cors");

const client = new Client({
  host: "http://elasticsearch:9200",
});

const app = express();

app.use(cors());

// Scrapes processo data - Adds it to ES index - Sends data information to H2 database as Sql Statement
app.get("/processos/:numeroProcesso", async (req, res) => {
  const options = {
    uri:
      "https://esaj.tjsp.jus.br/cpopg/show.do?processo.codigo=&processo.foro=&processo.numero=" +
      req.params.numeroProcesso,
    transform: (body) => cheerio.load(body),
  };

  // Checks if processo data already exists in ES index
  const INDEX_CHECK = await client.search({
    index: "processos",
    body: {
      query: {
        match: {
          numero: req.params.numeroProcesso,
        },
      },
    },
  });

  // Scrapes processo data from website
  rp(options)
    .then(($) => {
      const data = [];

      const processo = {
        numero: $("#numeroProcesso").text().trim(),
        dataDistribuicao: $("#dataHoraDistribuicaoProcesso").text().trim(),
        juiz: $("#juizProcesso").text().trim(),
        assunto: $("#assuntoProcesso").text().trim(),
        vara: $("#varaProcesso").text().trim(),
        area: $("#areaProcesso").text().trim(),
        valor: $("#valorAcaoProcesso").text().trim(),
        foro: $("#foroProcesso").text().trim().toLowerCase(),
        partesPrincipais: [],
        todasPartes: [],
        movimentacoes: [],
      };

      $("#tablePartesPrincipais tr").each(function (i, elem) {
        const parte = {
          tipo: $(this).find("td").eq(0).text().trim(),
          nomes: $(this).find("td").eq(1).text().trim(),
        };

        processo.partesPrincipais.push(
          "Tipo: " +
            parte.tipo +
            " Partes: " +
            parte.nomes
              .trim()
              .replace(/^\s+|\s+$/gm, "")
              .replace(/\n/g, " ")
        );
      });

      $("#tableTodasPartes tr").each(function (i, elem) {
        const parte = {
          tipo: $(this).find("td").eq(0).text().trim(),
          nomes: $(this).find("td").eq(1).text().trim(),
        };

        processo.todasPartes.push(
          "Tipo: " +
            parte.tipo +
            " Partes: " +
            parte.nomes
              .trim()
              .replace(/^\s+|\s+$/gm, "")
              .replace(/\n/g, " ")
        );
      });

      let sqlMov = "";

      $("#tabelaUltimasMovimentacoes tr").each(function (i, elem) {
        const movimentacao = {
          data: $(this).find(".dataMovimentacao").text().trim(),
          descricao: $(this).find(".descricaoMovimentacao").text().trim(),
        };

        processo.movimentacoes.push(
          "Data: " +
            movimentacao.data +
            " Descrição: " +
            movimentacao.descricao
              .trim()
              .replace(/^\s+|\s+$/gm, "")
              .replace(/\n/g, " ")
        );

        sqlMov += `INSERT INTO movimentacoes(cnjnumber_fk, data, descricao) VALUES ('${
          processo.numero
        }', '${movimentacao.data}', '${movimentacao.descricao
          .trim()
          .replace(/^\s+|\s+$/gm, "")
          .replace(/\n/g, " ")}');`;
      });

      processo.numero !== ""
        ? data.push(processo)
        : console.log("Processo não encontrado");

      const processos = data;
      let processoSQL = "";

      processos.forEach((processo) => {
        const {
          numero,
          dataDistribuicao,
          juiz,
          assunto,
          vara,
          area,
          valor,
          foro,
          partesPrincipais,
          todasPartes,
        } = processo;

        processoSQL +=
          `INSERT INTO processos (numero, data_distribuicao, juiz, assunto, vara, area, valor, foro, partes_principais, todas_partes) VALUES ('${numero}', '${dataDistribuicao}', '${juiz}', '${assunto}', '${vara}', '${area}', '${valor}', '${foro}', '${partesPrincipais.join(
            " - "
          )}', '${todasPartes.join(" - ")}');` + sqlMov;
      });

      // if matching "processo" is not found in ES index and data scraped from website exists, add it to ES index
      if (data.length !== 0) {
        const body = data.find((item) => item);
        if (INDEX_CHECK.hits.total.value === 0) {
          client.index({ index: "processos", body });
        } else {
          client.update({
            index: "processos",
            id: INDEX_CHECK.hits.hits[0]._id,
            body: { doc: body },
          });
        }
      }

      res.send(processoSQL);
    })
    .catch((err) => {
      console.log(err + " --- Dados não encontrados");
    });
});

app.get("/all-processos", async (_req, res) => {
  try {
    const body = await client.search({
      index: "processos",
      size: 6000,
    });

    const processos = body.hits.hits.map((hit) => hit._source);

    res.json(processos);
  } catch (err) {
    console.error(err);
    res.sendStatus(500);
  }
});

app.get("/processos/search/:searchType/:id", async (req, res) => {
  try {
    const { searchType, id } = req.params;
    const query = searchType === "cnj" ? "numero.keyword" : "foro.keyword";
    const body = await client.search({
      body: {
        query: {
          term: {
            [query]: id,
          },
        },
      },
    });
    if (body.hits.total.value != 0) {
      res.json(body.hits.hits.map((hit) => hit._source));
    } else {
      res.sendStatus(404);
    }
  } catch (err) {
    console.error(err);
    res.sendStatus(500);
  }
});

app.listen(PORT, () => {
  console.log(`Listening at http://localhost:${PORT}`);
});

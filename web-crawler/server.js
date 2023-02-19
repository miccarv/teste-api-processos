const express = require("express");
const rp = require("request-promise");
const cheerio = require("cheerio");

const app = express();

app.get("/processos/:numeroProcesso", (req, res) => {
  const options = {
    uri:
      "https://esaj.tjsp.jus.br/cpopg/show.do?processo.codigo=&processo.foro=&processo.numero=" +
      req.params.numeroProcesso,
    transform: function (body) {
      return cheerio.load(body);
    },
  };

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

      let sql = "";

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

        sql += `INSERT INTO movimentacoes(cnjnumber_fk, data, descricao) VALUES ('${
          processo.numero
        }', '${movimentacao.data}', '${movimentacao.descricao
          .trim()
          .replace(/^\s+|\s+$/gm, "")
          .replace(/\n/g, " ")}');`;
      });

      if (processo.numero !== "") {
        data.push(processo);
      }

      const processos = data;
      const processosLength = processos.length;
      let processoSQL = "";

      for (let i = 0; i < processosLength; i++) {
        const processo = processos[i];
        const numero = processo.numero;
        const dataDistribuicao = processo.dataDistribuicao;
        const juiz = processo.juiz;
        const assunto = processo.assunto;
        const vara = processo.vara;
        const area = processo.area;
        const valor = processo.valor;
        const foro = processo.foro;
        const partesPrincipais = processo.partesPrincipais.join(" - ");
        const todasPartes = processo.todasPartes.join(" - ");

        processoSQL +=
          `INSERT INTO processos (cnjnumber, data, juiz, assunto, vara, area, valor, tribunal_origem, partes_principais, todas_partes) VALUES ('${numero}', '${dataDistribuicao}', '${juiz}', '${assunto}', '${vara}', '${area}', '${valor}', '${foro}', '${partesPrincipais}', '${todasPartes}');` +
          sql;
      }

      res.send(processoSQL);
    })
    .catch((err) => {
      console.log(err + " --- Dados não encontrados");
    });
});

app.listen(8081, () => {
  console.log("Server started on port 8081");
});

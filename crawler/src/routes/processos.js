const rp = require("request-promise");
const cheerio = require("cheerio");
const request = require("request");

module.exports = async function (req, res) {
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

      $("#tabelaUltimasMovimentacoes tr").each(function (i, elem) {
        const movimentacao = {
          data: $(this).find(".dataMovimentacao").text().trim(),
          descricao: $(this)
            .find(".descricaoMovimentacao")
            .text()
            .trim()
            .replace(/^\s+|\s+$/gm, "")
            .replace(/\n/g, " "),
        };

        processo.movimentacoes.push(movimentacao);
      });
      
      const item = {
        ...processo,
        partesPrincipais: processo.partesPrincipais.join(" - "),
        todasPartes: processo.todasPartes.join(" - "),
      };
      
      if (item.numero !== "") return item;
    })
    .then((item) => {
      request.post(
        {
          url: "http://backend:8080/es/add-processo",
          json: true,
          body: item,
        },
        function optionalCallback(err, httpResponse, body) {
          if (err || httpResponse.statusCode !== 200)
            return res.status(400).send();
          return res.status(200).send();
        }
      );

      request.post(
        {
          url: "http://backend:8080/h2/add-processo",
          json: true,
          body: item,
        },
        function optionalCallback(err, httpResponse, body) {
          if (err || httpResponse.statusCode !== 200)
            return res.status(400).send();
          return res.status(200).send();
        }
      );

    })
    .catch((err) => {
      console.log(err);
      res.status(500).send();
    });
};

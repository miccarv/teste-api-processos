const rp = require("request-promise");
const cheerio = require("cheerio");
const request = require("request");

const OPTIONS_REQUEST = "https://esaj.tjsp.jus.br/cpopg/show.do?processo.codigo=&processo.foro=&processo.numero="

module.exports = function (req, res) {
  const options = {
    uri:
      OPTIONS_REQUEST + req.params.numeroProcesso,
    transform: function (body) {
      return cheerio.load(body);
    },
  };

  rp(options)
    .then(($) => {
      if ($("#numeroProcesso") == "") throw new Error("Não foi possível encontrar processo");

      const processoCrawleado = {
        numero: $("#numeroProcesso").text().trim(),
        dataDistribuicao: $("#dataHoraDistribuicaoProcesso").text().trim(),
        juiz: $("#juizProcesso").text().trim(),
        assunto: $("#assuntoProcesso").text().trim(),
        vara: $("#varaProcesso").text().trim(),
        area: $("#areaProcesso").text().trim(),
        valor: $("#valorAcaoProcesso").text().trim(),
        foro: $("#foroProcesso").text().trim().toLowerCase(),
        partesPrincipais: [],
        //todasPartes: [],
        movimentacoes: [],
      };
      $("#tablePartesPrincipais tr").each(function (i, elem) {
        const parte = {
          tipo: $(this).find("td").eq(0).text().trim(),
          nomes: $(this).find("td").eq(1).text().trim(),
        };

        processoCrawleado.partesPrincipais.push(
          "Tipo: " +
            parte.tipo +
            " Partes: " +
            parte.nomes
              .trim()
              .replace(/^\s+|\s+$/gm, "")
              .replace(/\n/g, " ")
        );
      });

      // $("#tableTodasPartes tr").each(function (i, elem) {
      //   const parte = {
      //     tipo: $(this).find("td").eq(0).text().trim(),
      //     nomes: $(this).find("td").eq(1).text().trim(),
      //   };

      //   processoCrawleado.todasPartes.push(
      //     "Tipo: " +
      //       parte.tipo +
      //       " Partes: " +
      //       parte.nomes
      //         .trim()
      //         .replace(/^\s+|\s+$/gm, "")
      //         .replace(/\n/g, " ")
      //   );
      // });

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

        processoCrawleado.movimentacoes.push(movimentacao);
      });
      
      processoCrawleado.partesPrincipais = processoCrawleado.partesPrincipais.join(" - ");
      //processoCrawleado.todasPartes = processoCrawleado.todasPartes.join(" - ");

      let statusCode = 500;

      function optionalCallback(args) {
        if (args == undefined) return;
        const { err, httpResponse, _body } = args;
        if (err || httpResponse.statusCode !== 200) {
          statusCode = httpResponse.statusCode;
        } else if(httpResponse.statusCode == 200){
          statusCode = 200;
        }
      }

      request.post(
        {
          url: "http://backend:8080/es/processos",
          json: true,
          body: processoCrawleado,
        },
        optionalCallback()
      );

      request.post(
        {
          url: "http://backend:8080/h2/processos",
          json: true,
          body: processoCrawleado,
        },
        optionalCallback()
      );

      res.status(statusCode).send(processoCrawleado);

    })
    .catch((err) => {
      console.log(err);
      res.status(500).send();
    });
};

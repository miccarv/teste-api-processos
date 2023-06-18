const rp = require("request-promise");
const cheerio = require("cheerio");
const sendMessage = require("../rabbitmq/sendMessage");

const OPTIONS_REQUEST =
  "https://esaj.tjsp.jus.br/cpopg/show.do?processo.codigo=&processo.foro=&processo.numero=";

module.exports = async (req, res) => {
  const options = {
    uri: `${OPTIONS_REQUEST}${req.params.numeroProcesso}`,
    transform: (body) => cheerio.load(body),
  };

  rp(options)
    .catch((err) => {
      throw new Error(err);
    })
    .then(($) => {
      if ($("#numeroProcesso") == "")
        throw new Error("Não foi possível encontrar processo");

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
        movimentacoes: [],
      };
      $("#tablePartesPrincipais tr").each(function () {
        const parte = {
          tipo: $(this).find("td").eq(0).text().trim(),
          nomes: $(this)
            .find("td")
            .eq(1)
            .text()
            .trim()
            .replace(/(^\s+|\s+|\n+$)/gm, " "),
        };

        processoCrawleado.partesPrincipais.push(
          `Tipo: ${parte.tipo} Partes: ${parte.nomes}`
        );
      });

      processoCrawleado.partesPrincipais =
        processoCrawleado.partesPrincipais.join(" - ");

      $("#tabelaUltimasMovimentacoes tr").each(function () {
        const movimentacao = {
          data: $(this).find(".dataMovimentacao").text().trim(),
          descricao: $(this)
            .find(".descricaoMovimentacao")
            .text()
            .trim()
            .replace(/(^\s+|\s+|\n+$)/gm, " "),
        };

        processoCrawleado.movimentacoes.push(movimentacao);
      });

      sendMessage(JSON.stringify(processoCrawleado)).catch(err => console.error(err));

      res.status(200).send(processoCrawleado);
    })
    .catch((err) => {
      console.error(err);
      res.status(500).send();
    });
};

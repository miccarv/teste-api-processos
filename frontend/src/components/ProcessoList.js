import React from "react";
 
const upperFirst = (str) => str.replace(/\b\w/, (l) => l.toUpperCase());

const ProcessoList = ({ items, searchStart }) => {
  return items.map((item) => (
    <div
      className={`mb-4 ${searchStart === false ? "invisible" : ""}`}
      key={item.numero}
    >
      <div className="card">
        <div className="card-header">
          <h5>Sobre o processo</h5>
        </div>
        <div className="card-body">
          <div className="card-title">
            <strong>Número CNJ / Processo:</strong> {item.numero}
          </div>
          <div className="card-text">
            <p>
              <strong>Juiz:</strong> {upperFirst(item.juiz.toLowerCase())}
            </p>
            <p>
              <strong>Vara:</strong> {item.vara}
            </p>
            <p>
              <strong>Assunto:</strong> {item.assunto}
            </p>
            <p>
              <strong>Valor:</strong> {item.valor}
            </p>
            <p>
              <strong>Área:</strong> {item.area}
            </p>
            <p>
            <strong>Todas as Partes:</strong> {item.todasPartes}
            </p>
            <p>
              <strong>Tribunal de origem:</strong> {upperFirst(item.foro)}
            </p>
            <p>
              <strong>Data de início:</strong> {item.dataDistribuicao}
            </p>
            {item._movimentacoes.map((mov) => (
              <p key={mov.index}>
                <strong>Movimentação:</strong> {mov.data} - {mov.descricao}
              </p>
            ))}
          </div>
        </div>
      </div>
    </div>
  ));
};

export default ProcessoList;

import React from "react";

const upperFirst = (str) => str.replace(/\b(\w)/, (l) => l.toUpperCase());

const ProcessoList = ({ processos }) => {
  return processos === undefined || null || processos.length === 0 ? (
    <div>Nenhum processo encontrado</div>
  ) : (
    processos.map((processo) => (
      <div className="mb-4" key={processo.numero}>
        <div className="card">
          <div className="card-header">
            <h5>Sobre o processo</h5>
          </div>
          <div className="card-body">
            <div className="card-title">
              <strong>Número CNJ / Processo:</strong> {processo.numero}
            </div>
            <div className="card-text">
              <p>
                <strong>Juiz:</strong> {upperFirst(processo.juiz.toLowerCase())}
              </p>
              <p>
                <strong>Vara:</strong> {processo.vara}
              </p>
              <p>
                <strong>Assunto:</strong> {processo.assunto}
              </p>
              <p>
                <strong>Valor:</strong> {processo.valor}
              </p>
              <p>
                <strong>Área:</strong> {processo.area}
              </p>
              <p>
                <strong>Todas as Partes:</strong> {processo.todasPartes}
              </p>
              <p>
                <strong>Tribunal de origem:</strong> {upperFirst(processo.foro)}
              </p>
              <p>
                <strong>Data de início:</strong> {processo.dataDistribuicao}
              </p>
              {processo._movimentacoes.map((mov) => (
                <p key={mov.index}>
                  <strong>Movimentação:</strong> {mov.data} - {mov.descricao}
                </p>
              ))}
            </div>
          </div>
        </div>
      </div>
    ))
  );
};

export default ProcessoList;

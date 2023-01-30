import "bootstrap/dist/css/bootstrap.min.css";
import { IMaskInput } from "react-imask";
import upperFirst from "./utilities/UpperFirst";
import React, { useState, useEffect } from "react";
import axios from "axios";

const API_URL = "http://localhost:8080/api";

const App = () => {
  const [items, setItems] = useState([]);
  const [searchCnj, setSearchCnj] = useState("");
  const [searchTribunal, setSearchTribunal] = useState("");
  const [searchType, setSearchType] = useState("");
  const CNJ = "0000000-00.0000.0.00.0000";
  const [searchStart, setSearchStart] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      const result = await axios.get(`${API_URL}/items`, {
        headers: {
          "Access-Control-Allow-Origin": "*",
        },
      });
      setItems(result.data);
    };
    fetchData();
  }, []);

  const handleSearch = async (event) => {
    if (searchCnj === "" && searchTribunal === "") {
      return alert("Preencha um dos campos");
    }
    event.preventDefault();

    setSearchType(searchCnj ? "processoCnj" : "processoTr");
    const searchValue = searchCnj || searchTribunal;
    try {
      const result = await axios.get(
        `${API_URL}/search?${searchType}=${searchValue.toLowerCase()}`,
        { headers: { "Access-Control-Allow-Origin": "*" } }
      );
      setItems(result.data);
    } catch (error) {
      if (error.response.status === 404) {
        setSearchStart(false);
        alert("Processo não encontrado");
      }
    }
  };

  return (
    <div className="container d-flex flex-column justify-content-center">
      <br />
      <form
        className="row w-100 h-50 my-4 py-3 border align-items-center"
        onSubmit={handleSearch}
      >
        <label className="col-2 text-center">Busca por:</label>
        <IMaskInput
          type="text"
          placeholder="Número de CNJ"
          mask={CNJ}
          maxLength={25}
          className="m-2 p-2 col-2"
          value={searchCnj}
          onChange={(e) => {
            setSearchCnj(e.target.value);
            setSearchType("processoCnj");
            setSearchTribunal("");
          }}
        />
        <input
          type="text"
          placeholder="Tribunal de origem"
          className="m-2 p-2 col-2"
          value={searchTribunal}
          onChange={(e) => {
            setSearchTribunal(e.target.value);
            setSearchType("processoTr");
            setSearchCnj("");
          }}
        />

        <button
          className="btn btn-secondary ms-2 col-2"
          style={{ height: "40px", maxWidth: "10rem" }}
          type="submit"
          onClick={(e) => {
            setSearchStart(true);
          }}
        >
          Buscar
        </button>
        <button
          className="btn btn-secondary ms-3 col-2"
          style={{ height: "40px", maxWidth: "10rem" }}
          type="button"
          onClick={async (e) => {
            const result = await axios.get(`${API_URL}/items`, {
              headers: { "Access-Control-Allow-Origin": "*" },
            });
            setItems(result.data);
            setSearchStart(true);
          }}
        >
          Processos
        </button>
      </form>
      {searchStart === false ? (
        <div className="row w-100 mt-3 border p-3">
          <h5 className="mb-4 mt-2">Dados para busca:</h5>
          <p>0000000-00.0000.0.00.0000 / Court of Law</p>
          <p>0000001-00.0000.0.00.0000 / Supreme Court</p>
          <p>0000002-00.0000.0.00.0000 / Federal Court</p>
          <p>0000003-00.0000.0.00.0000 / Federal Court</p>
          <p>0000004-00.0000.0.00.0000 / Federal Court</p>
        </div>
      ) : (
        <div className="row">
          {items.map((item) => (
            <div className="mb-4" key={item.cnjNumber}>
              <div className="card">
                <div className="card-header">
                  <h5>Sobre o processo</h5>
                </div>
                <div className="card-body">
                  <div className="card-title">
                    <strong>Número CNJ / Processo:</strong> {item.cnjNumber}
                  </div>
                  <div className="card-text">
                    <p>
                      <strong>Nome das partes:</strong> {item.partes}
                    </p>
                    <p>
                      <strong>Tribunal de origem:</strong>{" "}
                      {upperFirst(item.tribunalOrigem)}
                    </p>
                    <p>
                      <strong>Data de início:</strong> {item.data}
                    </p>
                    {item.movimentacoes.map((mov) => (
                      <p key={mov.data}>
                        <strong>Movimentações:</strong> {mov.data} -{" "}
                        {mov.description}
                      </p>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default App;

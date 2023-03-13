import "bootstrap/dist/css/bootstrap.min.css";
import { IMaskInput } from "react-imask";
import React, { useState, useEffect } from "react";
import axios from "../node_modules/axios/dist/axios.min";

const API_URL = "http://localhost:8081/all-processos";
const API_URL_1 = "http://localhost:8081/processos/search";
const upperFirst = (str) => str.replace(/\b\w/g, (l) => l.toUpperCase());

const App = () => {
  const [items, setItems] = useState([]);
  const [searchCnj, setSearchCnj] = useState("");
  const [searchTribunal, setSearchTribunal] = useState("");
  const [searchType, setSearchType] = useState("");
  const CNJ = "0000000-00.0000.0.00.0000";
  const [searchStart, setSearchStart] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      const result = await axios.get(API_URL, {
        headers: {
          "Access-Control-Allow-Origin": "*",
        },
      });
      setItems(result.data);
    };
    fetchData();
  }, []);

  async function dbUpdate() {
    if (searchCnj !== "") {
      try {
        const request = await axios.get(
          `http://localhost:8080/api/search?processoCnj=${searchCnj}`,
          { headers: { "Access-Control-Allow-Origin": "*" } }
        );
        return request;
      } catch (error) {
        console.error(error);
      }
    }
  }

  const handleSearch = async (event) => {
    if (searchCnj === "" && searchTribunal === "") {
      return alert("Preencha um dos campos");
    }

    setSearchStart(true);

    event.preventDefault();

    setSearchType(searchCnj ? "cnj" : "foro");
    const searchValue = searchCnj || searchTribunal;
    try {
      const result = await axios.get(
        `${API_URL_1}/${searchType}/${searchValue.toLowerCase()}`,
        { headers: { "Access-Control-Allow-Origin": "*" } }
      );
      setItems(result.data);
      dbUpdate();
    } catch (error) {
      if (error.response.status) {
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
            setSearchType("cnj");
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
            setSearchType("foro");
            setSearchCnj("");
          }}
        />

        <button
          className="btn btn-secondary ms-2 col-2"
          style={{ height: "40px", maxWidth: "10rem" }}
          type="submit"
        >
          Buscar
        </button>
        <button
          className="btn btn-secondary ms-3 col-2"
          style={{ height: "40px", maxWidth: "10rem" }}
          type="button"
          onClick={async (e) => {
            const result = await axios.get(API_URL, {
              headers: { "Access-Control-Allow-Origin": "*" },
            });
            setItems(result.data);
            setSearchStart(true);
          }}
        >
          Processos
        </button>
      </form>
      {searchStart === false || items.length === 0 ? (
        <div className="row w-100 mt-3 border p-3">
          <h5 className="mb-4 mt-2">Dados para busca:</h5>
          <p>1001353-64.2022.8.26.0268 / Foro de Itapecerica da Serra</p>
        </div>
      ) : (
        <div className="row">
          {items.map((item) => (
            <div className="mb-4" key={item.numero}>
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
                      <strong>Juiz:</strong>{" "}
                      {upperFirst(item.juiz.toLowerCase())}
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
                    {item.todasPartes.map((parte) => (
                      <p key={parte}>{parte}</p>
                    ))}
                    <p>
                      <strong>Tribunal de origem:</strong>{" "}
                      {upperFirst(item.foro)}
                    </p>
                    <p>
                      <strong>Data de início:</strong> {item.dataDistribuicao}
                    </p>
                    {item.movimentacoes.map((mov) => (
                      <p key={mov.data}>
                        <strong>Movimentação:</strong> {mov}
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

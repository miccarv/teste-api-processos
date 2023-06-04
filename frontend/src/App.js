import "bootstrap/dist/css/bootstrap.min.css";
import React, { useState } from "react";
import axios from "../node_modules/axios/dist/axios.min";
import SearchForm from "./components/SearchForm.js";
import ProcessoList from "./components/ProcessoList.js";

const API_URL = "http://localhost:8080/es/";

const headers = {
  headers: {
    "Access-Control-Allow-Origin": "*",
  },
};

function App() {
  const [searchCnj, setSearchCnj] = useState("");
  const [searchTribunal, setSearchTribunal] = useState("");
  const [searchType, setSearchType] = useState("");
  const [searchStart, setSearchStart] = useState(false);
  const [processos, setProcessos] = useState([]);

  const handleSearch = async (e) => {
    e.preventDefault();
    try {
      if (searchType == "cnj") {
        const result = await axios.get(
          `${API_URL}search-cnj?processoCnj=${searchCnj}`,
          headers
        );
        setProcessos(result.data);
        setSearchStart(true);
      } else if (searchType == "foro") {
        const result = await axios.get(
          `${API_URL}search-foro?processoForo=${searchTribunal}`,
          headers
        );
        setProcessos(result.data);
        setSearchStart(true);
      }
    } catch (error) {
      alert("resultado não encontrado");
    }
  };

  const crawlProcesso = async (e) => {
    e.preventDefault();
    const url = `http://localhost:8081/processos/${searchCnj}`;
    try {
      const response = await axios.get(url, headers);
      response.status === 200
        ? alert("Processo crawled")
        : alert("Não foi possível crawlear o processo");
    } catch (error) {
      console.error(error.status);
      alert("Número de processo inválido");
    }
  };

  const getProcessos = async (e) => {
    e.preventDefault();
    const url = "http://localhost:8080/es/processos";
    try {
      const response = await axios.get(url, headers);
      if (!response.data.length == 0) {
        setProcessos(response.data);
        setSearchTribunal("");
        setSearchCnj("");
        setSearchStart(true);
      } else {
        alert("Nenhum processo cadastrado");
      }
    } catch (error) {
      alert("Nenhum processo cadastrado");
      console.error(error);
    }
  };

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">Buscar Processos</h1>
      <SearchForm
        handleSearch={handleSearch}
        searchCnj={searchCnj}
        setSearchCnj={setSearchCnj}
        searchTribunal={searchTribunal}
        setSearchTribunal={setSearchTribunal}
        searchType={searchType}
        setSearchType={setSearchType}
        searchStart={searchStart}
        crawlProcesso={crawlProcesso}
        allProcessos={getProcessos}
      />
      <ProcessoList searchStart={searchStart} processos={processos} />
    </div>
  );
}

export default App;

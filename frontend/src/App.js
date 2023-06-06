import "bootstrap/dist/css/bootstrap.min.css";
import React, { useEffect, useState } from "react";
import SearchForm from "./components/SearchForm.js";
import ProcessoList from "./components/ProcessoList.js";
import { getProcessos } from "./handlerFunctions/getProcessos";

function App() {
  const [searchStart, setSearchStart] = useState(false);
  const [processos, setProcessos] = useState([]);

  useEffect(() => {
    getProcessos()
      .then(() => setProcessos)
      .catch((err) => console.error(err));
  }, []);

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">Buscar Processos</h1>
      <SearchForm setSearchStart={setSearchStart} setProcessos={setProcessos}/>
      {searchStart && <ProcessoList processos={processos} />}
    </div>
  );
}

export default App;

import React, { useState } from "react";
import { IMaskInput } from "react-imask";
import { searchByCnjOrForo } from "../api/searchByCnjOrForo";
import { crawlProcesso } from "../handlerFunctions/crawlProcessos";

const CNJ = "0000000-00.0000.0.00.0000";

const SearchForm = ({ setSearchStart, setProcessos }) => {
  const [cnjNumber, setCnjNumber] = useState("");
  const [tribunal, setTribunal] = useState("");
  const [searchType, setSearchType] = useState("");

  const handleSearch = async (e) => {
    e.preventDefault();
    try {
      const searchValue = cnjNumber || tribunal;
      const result = await searchByCnjOrForo(searchType, searchValue);
      setProcessos(result);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <form
      className="d-flex flex-wrap justify-content-evenly mb-3 p-3 border align-items-center bg-light rounded"
      onSubmit={handleSearch}
    >
      <div className="form-group">
        <IMaskInput
          type="text"
          placeholder="Número de CNJ"
          mask={CNJ}
          maxLength={25}
          className="form-control m-2"
          value={cnjNumber}
          onChange={(e) => {
            setCnjNumber(e.target.value);
            setSearchType("cnj");
            setTribunal("");
          }}
        />
        <input
          type="text"
          placeholder="Tribunal / Foro"
          className="form-control m-2"
          value={tribunal}
          onChange={(e) => {
            setTribunal(e.target.value);
            setSearchType("foro");
            setCnjNumber("");
          }}
        />
      </div>
      <div className="form-group">
        <button
          type="button"
          onClick={crawlProcesso}
          className="btn btn-secondary mx-2 my-2 w-100"
        >
          Crawl Processo
        </button>
        <button className="btn btn-secondary mx-2 my-2 w-100" type="submit">
          Buscar
        </button>
        <button
          className="btn btn-secondary mx-2 my-2 w-100"
          type="button"
          onClick={(e) => {
            e.preventDefault();
            setSearchStart(true);
          }}
        >
          Processos
        </button>
      </div>
    </form>
  );
};

export default SearchForm;

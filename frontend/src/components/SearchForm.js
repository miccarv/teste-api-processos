import React from "react";
import { IMaskInput } from "react-imask";

const CNJ = "0000000-00.0000.0.00.0000";

const SearchForm = ({
  handleSearch,
  searchCnj,
  setSearchCnj,
  searchTribunal,
  setSearchTribunal,
  setSearchType,
  crawlProcesso,
  allProcessos,
}) => {
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
          value={searchCnj}
          onChange={(e) => {
            setSearchCnj(e.target.value);
            setSearchType("cnj");
            setSearchTribunal("");
          }}
        />
        <input
          type="text"
          placeholder="Tribunal / Foro"
          className="form-control m-2"
          value={searchTribunal}
          onChange={(e) => {
            setSearchTribunal(e.target.value);
            setSearchType("foro");
            setSearchCnj("");
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
          onClick={allProcessos}
        >
          Processos
        </button>
      </div>
    </form>
  );
};

export default SearchForm;

import axios from "axios/dist/axios.min";
import { headers } from "./headers";

const API_URL = process.env.REACT_APP_API_URL;

export const searchByCnjOrForo = async (searchType, searchValue) => {
  const searchQuery =
    searchType === "cnj"
      ? "search-cnj?processoCnj="
      : "search-foro?processoForo=";
  try {
    const response = await axios.get(
      `${API_URL}/${searchQuery}${searchValue}`,
      headers
    );
    return response.status === 200
      ? response.data
      : alert("Não foi possível encontrar processo");
  } catch (err) {
    console.error(err);
  }
};

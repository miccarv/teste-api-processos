import axios from "axios/dist/axios.min";
import { headers } from "./headers";

const API_URL = process.env.REACT_APP_API_URL;

export const searchByCnjOrForo = async (searchType, searchValue) => {
  const searchQuery =
    searchType === "cnj"
      ? "search-cnj?processoCnj="
      : "search-foro?processoForo=";
  try {
    const result = await axios.get(
      `${API_URL}${searchQuery}${searchValue}`,
      headers
    );
    return result.status === 200
      ? result.data
      : alert("Não foi possível encontrar processo");
  } catch (err) {
    console.error(err);
  }
};

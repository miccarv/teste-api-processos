import axios from "../../node_modules/axios/dist/axios.min";
import { headers } from "../api/headers";

const API_URL = process.env.REACT_APP_API_CRAWLER_URL;

export const crawlProcesso = async (searchCnj) => {
  const url = `${API_URL}${searchCnj}`;
  try {
    const response = await axios.get(url, headers);
    response.status === 200
      ? alert("Processo crawled")
      : alert("Não foi possível crawlear o processo");
  } catch (err) {
    console.error(err);
    alert("Erro ao crawlear processo");
  }
};

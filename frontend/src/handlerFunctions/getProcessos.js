import axios from "../../node_modules/axios/dist/axios.min";
import { headers } from "../api/headers";

const API_URL = process.env.REACT_APP_API_PROCESSOS_URL;

export const getProcessos = async () => {
  try {
    const response = await axios.get(API_URL, headers);
    if (response.data.length > 0) {
      return response.data;
    } else {
      alert("Nenhum processo cadastrado");
    }
  } catch (err) {
    console.error(err);
  }
};

const express = require("express");
const PORT = 8081;
const cors = require("cors");
const processos = require("./src/routes/processos.js");

const app = express();

app.use(cors());

app.get("/processos/:numeroProcesso", processos);

app.listen(PORT, () => {
  console.log(`Listening at http://localhost:${PORT}`);
});

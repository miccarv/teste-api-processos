const express = require("express");
const cors = require("cors");
const crawler = require("./src/routes/crawler.js");
const PORT = 8081;

const app = express();

app.use(cors());

app.get("/processos/:numeroProcesso", crawler);

app.listen(PORT, () => {
  console.log(`Listening at http://localhost:${PORT}`);
});

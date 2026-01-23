const API_URL = "http://localhost:8080/autores";

async function criarAutor(autor) {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(autor)
  });

  if (!response.ok) {
    throw new Error("Erro ao salvar autor");
  }
}

async function buscarAutores() {
  const response = await fetch(API_URL);
  return response.json();
}

async function excluirAutor(id) {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "DELETE"
  });

  if (!response.ok) {
    throw new Error("Erro ao excluir autor");
  }
}

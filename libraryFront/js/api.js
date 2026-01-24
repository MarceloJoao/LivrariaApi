const API_URL = "http://localhost:8080/autores";
const API_LIVROS = "http://localhost:8080/livros"
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

async function editarAutor(id, autor) {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(autor)
  });

  if (!response.ok) {
    throw new Error("Erro ao editar autor");
  }
}

async function criarLivro(livro) {
  const response = await fetch(API_LIVROS, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(livro)
  });

  if (!response.ok) throw new Error("Erro ao salvar livro");
}

async function buscarLivros() {
  const response = await fetch(API_LIVROS);
  return response.json();
}

async function excluirLivro(id) {
  const response = await fetch(`${API_LIVROS}/${id}`, {
    method: "DELETE"
  });

  if (!response.ok) throw new Error("Erro ao excluir livro");
}

async function editarLivro(id, livro) {
  const response = await fetch(`${API_LIVROS}/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(livro)
  });

  if (!response.ok) {
    throw new Error("Erro ao editar livro");
  }
}

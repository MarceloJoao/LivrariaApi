document.addEventListener("DOMContentLoaded", () => {
  carregarAutores();

  document.getElementById("formLivro")
    .addEventListener("submit", salvarLivro);
});

async function carregarAutores() {
  const select = document.getElementById("autorId");
  const autores = await buscarAutores();

  select.innerHTML = "<option value=''>Selecione</option>";

  autores.forEach(autor => {
    const option = document.createElement("option");
    option.value = autor.id;
    option.textContent = autor.nome;
    select.appendChild(option);
  });
}

async function salvarLivro(e) {
  e.preventDefault();

  const autorIdSelecionado = document.getElementById("autorId").value;
  const generoSelecionado = document.getElementById("genero").value;

  
  if (!autorIdSelecionado) {
    document.getElementById("mensagem").innerHTML = `
      <div class="alert alert-warning">Por favor, selecione um autor antes de salvar.</div>
    `;
    return;
  }

  const livro = {
    titulo: document.getElementById("titulo").value,
    isbn: document.getElementById("isbn").value,
    dataPublicacao: document.getElementById("dataPublicacao").value,  
    genero: generoSelecionado === "" ? null : generoSelecionado, 
    preco: parseFloat(document.getElementById("preco").value) || 0,
    autorId: autorIdSelecionado 
  };

  try {
    console.log("Enviando para o Record DTO:", livro); 
    await criarLivro(livro);

    document.getElementById("mensagem").innerHTML = `
      <div class="alert alert-success">Livro salvo com sucesso!</div>
    `;

    e.target.reset();
  } catch (error) {
    console.error("Erro ao salvar:", error);
    document.getElementById("mensagem").innerHTML = `
      <div class="alert alert-danger">Erro ao salvar livro. Verifique o console.</div>
    `;
  }
}
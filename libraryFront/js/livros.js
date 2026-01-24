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

  const livro = {
    titulo: document.getElementById("titulo").value,
    isbn: document.getElementById("isbn").value,
    dataPublicacao: document.getElementById("dataPublicacao").value,
    genero: document.getElementById("genero").value,
    preco: document.getElementById("preco").value,
    autorId: document.getElementById("autorId").value
  };

  await criarLivro(livro);

  document.getElementById("mensagem").innerHTML = `
    <div class="alert alert-success">
      Livro salvo com sucesso!
    </div>
  `;

  e.target.reset();
}

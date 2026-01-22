function mostrarCadastro() {
  document.getElementById("conteudo").innerHTML = `
    <h2>Novo Autor</h2>

    <input id="nome" placeholder="Nome" />
    <input id="nacionalidade" placeholder="Nacionalidade" />
    <input id="dataNascimento" type="date" />

    <button onclick="salvarAutor()">Salvar</button>
  `;
}

async function salvarAutor() {
  const autor = {
    nome: document.getElementById("nome").value,
    nacionalidade: document.getElementById("nacionalidade").value,
    dataNascimento: document.getElementById("dataNascimento").value
  };

  try {
    await criarAutor(autor);
    alert("Autor salvo com sucesso!");
    listarAutores();
  } catch (e) {
    alert(e.message);
  }
}

async function listarAutores() {
  const autores = await buscarAutores();

  document.getElementById("conteudo").innerHTML = `
    <h2>Autores</h2>
    <ul>
      ${autores.map(a => `<li>${a.nome} - ${a.nacionalidade}</li>`).join("")}
    </ul>
  `;
}

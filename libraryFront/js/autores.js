const form = document.getElementById("autorForm");
const mensagem = document.getElementById("mensagem");

form.addEventListener("submit", async (event) => {
  event.preventDefault(); // ⬅️ ESSENCIAL

  const autor = {
    nome: document.getElementById("nome").value,
    nacionalidade: document.getElementById("nacionalidade").value,
    dataNascimento: document.getElementById("dataNascimento").value
  };

  try {
    await criarAutor(autor);

    mensagem.innerHTML = `
      <div class="alert alert-success">
        Autor cadastrado com sucesso!
      </div>
    `;

    form.reset();

  } catch (error) {
    mensagem.innerHTML = `
      <div class="alert alert-danger">
        Não foi possível conectar ao backend
      </div>
    `;
  }
});

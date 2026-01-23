document.addEventListener("DOMContentLoaded", listarAutores);

async function listarAutores() {
  const tabela = document.getElementById("tabelaAutores");
  const mensagem = document.getElementById("mensagem");

  try {
    const autores = await buscarAutores();

    tabela.innerHTML = "";
    mensagem.innerHTML = "";

    if (autores.length === 0) {
      tabela.innerHTML = `
        <tr>
          <td colspan="4" class="text-center">Nenhum autor cadastrado</td>
        </tr>
      `;
      return;
    }

    autores.forEach(autor => {
      const linha = document.createElement("tr");

      linha.innerHTML = `
        <td>${autor.nome}</td>
        <td>${autor.nacionalidade}</td>
        <td>${formatarData(autor.dataNascimento)}</td>
        <td>
          <button class="btn btn-danger btn-sm">
            Excluir
          </button>
        </td>
      `;

      linha.querySelector("button").addEventListener("click", async () => {
        const confirmar = confirm(`Deseja excluir o autor ${autor.nome}?`);

        if (!confirmar) return;

        try {
          await excluirAutor(autor.id);
          listarAutores(); // recarrega lista
        } catch (e) {
          alert("Erro ao excluir autor");
        }
      });

      tabela.appendChild(linha);
    });

  } catch (error) {
    mensagem.innerHTML = `
      <div class="alert alert-danger">
        Erro ao carregar autores
      </div>
    `;
  }
}

function formatarData(data) {
  if (!data) return "-";
  return data.split("-").reverse().join("/");
}

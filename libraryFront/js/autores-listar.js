document.addEventListener("DOMContentLoaded", listarAutores);

async function listarAutores() {
  const tabela = document.getElementById("tabelaAutores");
  const mensagem = document.getElementById("mensagem");

  try {
    const autores = await buscarAutores();

    tabela.innerHTML = "";

    if (autores.length === 0) {
      tabela.innerHTML = `
        <tr>
          <td colspan="3" class="text-center">Nenhum autor cadastrado</td>
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
      `;

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

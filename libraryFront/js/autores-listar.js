document.addEventListener("DOMContentLoaded", () => {
  listarAutores();
});

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
          <td colspan="4" class="text-center">
            Nenhum autor cadastrado
          </td>
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
          <button class="btn btn-warning btn-sm me-2 btn-editar">
            Editar
          </button>
          <button class="btn btn-danger btn-sm btn-excluir">
            Excluir
          </button>
        </td>
      `;

      linha.querySelector(".btn-editar").addEventListener("click", () => {
        abrirModalEditar(autor);
      });

      linha.querySelector(".btn-excluir").addEventListener("click", async () => {
        const confirmar = confirm(
          `Deseja excluir o autor ${autor.nome}?`
        );

        if (!confirmar) return;

        try {
          await excluirAutor(autor.id);

          mensagem.innerHTML = `
            <div class="alert alert-success alert-dismissible fade show">
              Autor <strong>${autor.nome}</strong> excluído com sucesso!
              <button class="btn-close" data-bs-dismiss="alert"></button>
            </div>
          `;

          listarAutores();
        } catch (e) {
          mensagem.innerHTML = `
            <div class="alert alert-danger">
              Erro ao excluir autor
            </div>
          `;
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

/* =========================
   MODAL EDITAR
========================= */
function abrirModalEditar(autor) {
  document.getElementById("editId").value = autor.id;
  document.getElementById("editNome").value = autor.nome;
  document.getElementById("editNacionalidade").value = autor.nacionalidade;
  document.getElementById("editDataNascimento").value = autor.dataNascimento;

  const modal = new bootstrap.Modal(
    document.getElementById("modalEditarAutor")
  );
  modal.show();
}

document.getElementById("btnSalvarEdicao")
  .addEventListener("click", async () => {

  const id = document.getElementById("editId").value;

  const autor = {
    nome: document.getElementById("editNome").value,
    nacionalidade: document.getElementById("editNacionalidade").value,
    dataNascimento: document.getElementById("editDataNascimento").value
  };

  try {
    await editarAutor(id, autor);

    bootstrap.Modal.getInstance(
      document.getElementById("modalEditarAutor")
    ).hide();

    document.getElementById("mensagem").innerHTML = `
      <div class="alert alert-success alert-dismissible fade show">
        Autor editado com sucesso!
        <button class="btn-close" data-bs-dismiss="alert"></button>
      </div>
    `;

    listarAutores();

  } catch (e) {
    alert("Erro ao editar autor");
  }
});


function formatarData(data) {
  if (!data) return "-";
  return data.split("-").reverse().join("/");
}

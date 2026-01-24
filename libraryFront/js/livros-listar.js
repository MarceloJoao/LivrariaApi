document.addEventListener("DOMContentLoaded", listarLivros);

async function listarLivros() {
  const tabela = document.getElementById("tabelaLivros");
  const mensagem = document.getElementById("mensagem");

  try {
    const livros = await buscarLivros();

    tabela.innerHTML = "";
    mensagem.innerHTML = "";

    if (livros.length === 0) {
      tabela.innerHTML = `
        <tr>
          <td colspan="5" class="text-center">
            Nenhum livro cadastrado
          </td>
        </tr>
      `;
      return;
    }

    livros.forEach(livro => {
      const linha = document.createElement("tr");

      linha.innerHTML = `
        <td>${livro.titulo}</td>
        <td>${livro.autor?.nome ?? "-"}</td>
        <td>${livro.genero}</td>
        <td>R$ ${formatarPreco(livro.preco)}</td>
        <td>
          <button class="btn btn-warning btn-sm me-1">Editar</button>
          <button class="btn btn-danger btn-sm">Excluir</button>
        </td>
      `;

      const btnEditar = linha.querySelector(".btn-warning");
      const btnExcluir = linha.querySelector(".btn-danger");

      btnEditar.addEventListener("click", () => abrirModalEditarLivro(livro));
      btnExcluir.addEventListener("click", () => excluirLivroHandler(livro.id));

      tabela.appendChild(linha);
    });

  } catch (e) {
    mensagem.innerHTML = `
      <div class="alert alert-danger">
        Erro ao carregar livros
      </div>
    `;
  }
}

/* =========================
   EXCLUIR
========================= */
async function excluirLivroHandler(id) {
  const confirmar = confirm("Deseja excluir este livro?");
  if (!confirmar) return;

  try {
    await excluirLivro(id);

    document.getElementById("mensagem").innerHTML = `
      <div class="alert alert-success alert-dismissible fade show">
        Livro excluído com sucesso!
        <button class="btn-close" data-bs-dismiss="alert"></button>
      </div>
    `;

    listarLivros();
  } catch (e) {
    alert("Erro ao excluir livro");
  }
}

/* =========================
   EDITAR (MODAL)
========================= */
async function abrirModalEditarLivro(livro) {
  document.getElementById("editLivroId").value = livro.id;
  document.getElementById("editTitulo").value = livro.titulo;
  document.getElementById("editIsbn").value = livro.isbn;
  document.getElementById("editDataPublicacao").value = livro.dataPublicacao;
  document.getElementById("editGenero").value = livro.genero;
  document.getElementById("editPreco").value = livro.preco;

  await carregarAutoresModal(livro.autor?.id);

  const modal = new bootstrap.Modal(
    document.getElementById("modalEditarLivro")
  );
  modal.show();
}

/* =========================
   AUTORES NO MODAL
========================= */
async function carregarAutoresModal(autorSelecionadoId) {
  const select = document.getElementById("editAutorId");
  const autores = await buscarAutores();

  select.innerHTML = "";

  autores.forEach(autor => {
    const option = document.createElement("option");
    option.value = autor.id;
    option.textContent = autor.nome;

    if (autor.id === autorSelecionadoId) {
      option.selected = true;
    }

    select.appendChild(option);
  });
}

/* =========================
   SALVAR EDIÇÃO
========================= */
document.getElementById("btnSalvarEdicaoLivro")
  .addEventListener("click", async () => {

  const id = document.getElementById("editLivroId").value;

  const livro = {
    titulo: document.getElementById("editTitulo").value,
    isbn: document.getElementById("editIsbn").value,
    dataPublicacao: document.getElementById("editDataPublicacao").value,
    genero: document.getElementById("editGenero").value,
    preco: document.getElementById("editPreco").value,
    autorId: document.getElementById("editAutorId").value
  };

  try {
    await editarLivro(id, livro);

    bootstrap.Modal.getInstance(
      document.getElementById("modalEditarLivro")
    ).hide();

    document.getElementById("mensagem").innerHTML = `
      <div class="alert alert-success alert-dismissible fade show">
        Livro editado com sucesso!
        <button class="btn-close" data-bs-dismiss="alert"></button>
      </div>
    `;

    listarLivros();

  } catch (e) {
    alert("Erro ao editar livro");
  }
});

/* =========================
   UTIL
========================= */
function formatarPreco(preco) {
  if (!preco) return "0,00";
  return Number(preco).toFixed(2).replace(".", ",");
}

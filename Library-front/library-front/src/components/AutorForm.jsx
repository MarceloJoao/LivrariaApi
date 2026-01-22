import { useState } from "react";
import { api } from "../services/api";

function AutorForm({ onAutorSalvo }) {
  const [nome, setNome] = useState("");
  const [nacionalidade, setNacionalidade] = useState("");
  const [dataNascimento, setDataNascimento] = useState("");
  const [erro, setErro] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setErro("");

    try {
      await api.post("/autores", {
        nome,
        nacionalidade,
        dataNascimento // yyyy-MM-dd (vem certo do input type="date")
      });

      // limpa formulário
      setNome("");
      setNacionalidade("");
      setDataNascimento("");

      // avisa o componente pai (Autores.jsx)
      if (onAutorSalvo) {
        onAutorSalvo();
      }

    } catch (error) {
      console.error(error);
      setErro("Erro ao salvar autor");
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <h3>Novo Autor</h3>

      {erro && <p style={{ color: "red" }}>{erro}</p>}

      <input
        type="text"
        placeholder="Nome"
        value={nome}
        onChange={(e) => setNome(e.target.value)}
        required
      />

      <input
        type="text"
        placeholder="Nacionalidade"
        value={nacionalidade}
        onChange={(e) => setNacionalidade(e.target.value)}
        required
      />

      <input
        type="date"
        value={dataNascimento}
        onChange={(e) => setDataNascimento(e.target.value)}
        required
      />

      <button type="submit">Salvar</button>
    </form>
  );
}

export default AutorForm;

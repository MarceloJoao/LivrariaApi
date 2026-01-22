import { useEffect, useState } from "react";
import { api } from "../services/api";
import AutorForm from "../components/AutorForm";

function Autores() {
  const [autores, setAutores] = useState([]);

  const carregarAutores = () => {
    api.get("/autores")
      .then(response => setAutores(response.data))
      .catch(error => console.error(error));
  };

  useEffect(() => {
    carregarAutores();
  }, []);

  return (
    <div>
      <h2>Autores</h2>

      <AutorForm onAutorSalvo={carregarAutores} />

      <ul>
        {autores.map(autor => (
          <li key={autor.id}>
            {autor.nome} - {autor.nacionalidade}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Autores;

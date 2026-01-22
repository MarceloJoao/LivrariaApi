import { useEffect, useState } from "react";
import { api } from "../services/api";

function Autores() {
  const [autores, setAutores] = useState([]);

  useEffect(() => {
    api.get("/autores")
      .then(response => setAutores(response.data))
      .catch(error => console.error(error));
  }, []);

  return (
    <div>
      <h2>Autores</h2>

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

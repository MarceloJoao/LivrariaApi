package io.github.MarceloJoao.libraryapi.validator;


import io.github.MarceloJoao.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.MarceloJoao.libraryapi.model.Autor;
import io.github.MarceloJoao.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {

        Optional<Autor> autorEncontrado =
                autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()
                );

        // ✅ NÃO EXISTE AUTOR IGUAL → OK
        if (autorEncontrado.isEmpty()) {
            return false;
        }

        // ✅ CREATE (autor novo)
        if (autor.getId() == null) {
            return true;
        }

        // ✅ UPDATE: verifica se é outro autor
        return !autor.getId().equals(autorEncontrado.get().getId());
    }
}
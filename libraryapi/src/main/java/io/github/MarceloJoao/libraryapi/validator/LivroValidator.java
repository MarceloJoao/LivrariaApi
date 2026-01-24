package io.github.MarceloJoao.libraryapi.validator;

import io.github.MarceloJoao.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.MarceloJoao.libraryapi.model.Livro;
import io.github.MarceloJoao.libraryapi.repository.LivroRepository;
import org.springframework.stereotype.Component;

@Component
public class LivroValidator {

    private final LivroRepository repository;

    public LivroValidator(LivroRepository repository) {
        this.repository = repository;
    }

    public void validar(Livro livro) {

        // CADASTRO
        if (livro.getId() == null) {
            if (repository.existsByIsbn(livro.getIsbn())) {
                throw new RegistroDuplicadoException("ISBN já cadastrado");
            }
            return;
        }

        // ATUALIZAÇÃO
        if (repository.existsByIsbnAndIdNot(livro.getIsbn(), livro.getId())) {
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }
    }
}

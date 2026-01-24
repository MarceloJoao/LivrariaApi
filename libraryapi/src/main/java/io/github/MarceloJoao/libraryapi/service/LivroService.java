package io.github.MarceloJoao.libraryapi.service;

import io.github.MarceloJoao.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.MarceloJoao.libraryapi.model.Autor;
import io.github.MarceloJoao.libraryapi.model.Livro;
import io.github.MarceloJoao.libraryapi.repository.AutorRepository;
import io.github.MarceloJoao.libraryapi.repository.LivroRepository;
import io.github.MarceloJoao.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final LivroValidator validator;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(UUID id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() ->
                        new OperacaoNaoPermitidaException("Livro não encontrado")
                );
        livroRepository.delete(livro);
    }

   public void atualizar(UUID id, Livro livroAtualizado) {
    Livro livro = livroRepository.findById(id)
            .orElseThrow(() ->
                    new OperacaoNaoPermitidaException("Livro não encontrado")
            );

     
    livroAtualizado.setId(id);

    validator.validar(livroAtualizado);

    livro.setTitulo(livroAtualizado.getTitulo());
    livro.setIsbn(livroAtualizado.getIsbn());
    livro.setPreco(livroAtualizado.getPreco());
    livro.setGenero(livroAtualizado.getGenero());
    livro.setDataPublicacao(livroAtualizado.getDataPublicacao());
    livro.setAutor(livroAtualizado.getAutor());

    livroRepository.save(livro);
}


    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTitulo(titulo);
    }

    public List<Livro> buscarPorIsbn(String isbn) {
        return livroRepository.findByIsbn(isbn);
    }

    public List<Livro> buscarPorAutor(UUID autorId) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() ->
                        new OperacaoNaoPermitidaException("Autor não encontrado")
                );
        return livroRepository.findByAutor(autor);
    }
}

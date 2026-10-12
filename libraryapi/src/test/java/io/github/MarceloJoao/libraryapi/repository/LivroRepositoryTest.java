package io.github.MarceloJoao.libraryapi.repository;

import io.github.MarceloJoao.libraryapi.model.Autor;
import io.github.MarceloJoao.libraryapi.model.GeneroLivro;
import io.github.MarceloJoao.libraryapi.model.Livro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes de Integração de Repositório - LivroRepository")
class LivroRepositoryTest {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    @Transactional
    @DisplayName("Deve salvar livro com relacionamento em cascata de autor")
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("9863535-CAS");
        livro.setPreco(BigDecimal.valueOf(120.00));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Senhor dos Anéis");
        livro.setDataPublicacao(LocalDate.of(1954, 7, 29));

        Autor autor = new Autor();
        autor.setNome("J. R. R. Tolkien");
        autor.setNacionalidade("Reino Unido");
        autor.setDataNascimento(LocalDate.of(1892, 1, 3));

        livro.setAutor(autor);

        Livro livroSalvo = repository.save(livro);

        // Asserções formais de QA: Validando persistência e integridade dos dados
        assertNotNull(livroSalvo.getId(), "O ID do livro gerado não deveria ser nulo.");
        assertNotNull(livroSalvo.getAutor().getId(), "O autor vinculado deveria ter sido persistido.");
        assertEquals("Senhor dos Anéis", livroSalvo.getTitulo(), "O título salvo deve ser idêntico ao informado.");
        assertEquals(GeneroLivro.FICCAO, livroSalvo.getGenero());
    }

    @Test
    @Transactional
    @DisplayName("Deve salvar livro vinculado a autor existente")
    void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("George R. R. Martin");
        autor.setNacionalidade("EUA");
        autor.setDataNascimento(LocalDate.of(1948, 9, 20));
        autor = autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("9863535-SAVE");
        livro.setPreco(BigDecimal.valueOf(150.00));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("A Guerra dos Tronos");
        livro.setDataPublicacao(LocalDate.of(1996, 8, 1));
        livro.setAutor(autor);

        Livro livroSalvo = repository.save(livro);

        assertNotNull(livroSalvo.getId());
        assertEquals("A Guerra dos Tronos", livroSalvo.getTitulo());
        assertEquals(autor.getId(), livroSalvo.getAutor().getId(), "O autor associado deve corresponder ao autor salvo.");
    }

    @Test
    @DisplayName("Deve pesquisar livro por título com sucesso")
    void pesquisaPorTituloTest() {
        List<Livro> lista = repository.findByTitulo("A historia dos pets");
        assertNotNull(lista, "A lista de retorno não deve ser nula.");
    }

    @Test
    @DisplayName("Deve pesquisar livro por ISBN com retorno da consulta")
    void pesquisarPorISBNTest() {
        List<Livro> lista = repository.findByIsbn("9863535");
        assertNotNull(lista);
    }

    @Test
    @DisplayName("Deve consultar livros com JPQL personalizada ordenados por título e preço")
    void listarLivrosComQueryJPQL() {
        var resultado = repository.ListarTodosOrdenadoPorTituloAndPreco();
        assertNotNull(resultado, "A lista JPQL não deve ser nula.");
    }

    @Test
    @DisplayName("Deve filtrar livros por gênero específico")
    void listarPorGeneroQueryParamTest() {
        var resultado = repository.findByGenero(GeneroLivro.FICCAO);
        assertNotNull(resultado);
        assertTrue(resultado.stream().allMatch(l -> l.getGenero() == GeneroLivro.FICCAO),
                "Todos os livros retornados devem pertencer ao gênero informado.");
    }
}
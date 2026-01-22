package io.github.MarceloJoao.libraryapi.repository;

import io.github.MarceloJoao.libraryapi.model.Autor;
import io.github.MarceloJoao.libraryapi.model.GeneroLivro;
import io.github.MarceloJoao.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;
    @Autowired
    private AutorRepository autorRepository;

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("9863535");
        livro.setPreco(BigDecimal.valueOf(120));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("SJM");
        livro.setDataPublicacao(LocalDate.of(1980, 2, 25));

        Autor autor = new Autor();
        autor.setNome("Maria Luiza");
        autor.setNacionalidade("EUA");
        autor.setDataNascimento(LocalDate.of(1953, 1, 31));

        livro.setAutor(autor);

        repository.save(livro);
    }
    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("9863535");
        livro.setPreco(BigDecimal.valueOf(120));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("SJM");
        livro.setDataPublicacao(LocalDate.of(1980, 2, 25));

        Autor autor = new Autor();
        autor.setNome("Maria Luiza");
        autor.setNacionalidade("EUA");
        autor.setDataNascimento(LocalDate.of(1953, 1, 31));

        autorRepository.save(autor);
        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorLivro(){
        var livro = repository.findById(UUID.fromString("c21696ef-f252-4289-a140-c75b1cac0529")).orElse(null);

        var autor = autorRepository.findById(UUID.fromString("8f9ad19f-0a66-4b19-a901-d5c0f19bbbe5")).orElse(null);

        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("c21696ef-f252-4289-a140-c75b1cac0529");
        repository.deleteById(id);
    }

    @Test
    void deletarCascade(){
        UUID id = UUID.fromString("c21696ef-f252-4289-a140-c75b1cac0529");
        repository.deleteById(id);
    }

    @Test
    void buscarLivroTest(){
        UUID id = UUID.fromString("c21696ef-f252-4289-a140-c75b1cac0529");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());

    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> lista = repository.findByTitulo("A historia dos pets");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorISBNTest(){
        List<Livro> lista = repository.findByIsbn("9863535");
        lista.forEach(System.out::println);
    }
    @Test
    void pesquisarPorTituloEPrecoTest(){
        List<Livro> lista = repository.findByTituloAndPreco("A historia dos pets", BigDecimal.valueOf(150));
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.ListarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarNomesDiferentesLivros(){
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest(){
        var resultado = repository.findByGenero(GeneroLivro.FICCAO);
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPosParamTest(){
        var resultado = repository.findByGeneroPosicional(GeneroLivro.FICCAO);
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest(){
        repository.deleteByGenero(GeneroLivro.FICCAO);
    }

    @Test
    void atualizarDataPublicacaoTest(){
        repository.updateDataPublicacao(LocalDate.of(2020, 1, 1));
    }
}
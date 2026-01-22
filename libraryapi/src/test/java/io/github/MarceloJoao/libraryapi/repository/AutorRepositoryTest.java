package io.github.MarceloJoao.libraryapi.repository;

import io.github.MarceloJoao.libraryapi.model.Autor;
import io.github.MarceloJoao.libraryapi.model.GeneroLivro;
import io.github.MarceloJoao.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;
    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("EUA");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        var autorSalvo = repository.save(autor);

        System.out.println("Autor salvo com sucesso!" + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("8f9ad19f-0a66-4b19-a901-d5c0f19bbbe5");

        Optional<Autor> autor = repository.findById(id);

        if(autor.isPresent()){
            Autor autorAtualizado = autor.get();
            System.out.println("Dados do Autor:" + autor.get());

            autorAtualizado.setNome("Bebeto");
            autorAtualizado.setNacionalidade("Chinês");

            repository.save(autorAtualizado);
        }
    }

    @Test
    public void ListarTest(){
        List<Autor> autores = repository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("8f9ad19f-0a66-4b19-a901-d5c0f19bbbe5");
        repository.deleteById(id);
    }

    @Test
    void salvarAutorComLivro(){
        Autor autor = new Autor();
        autor.setNome("Maria Silva");
        autor.setNacionalidade("EUA");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        Livro livro = new Livro();
        livro.setIsbn("9863535-000");
        livro.setPreco(BigDecimal.valueOf(120));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Livro dos crias");
        livro.setDataPublicacao(LocalDate.of(1990, 2, 25));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("9863535-120");
        livro2.setPreco(BigDecimal.valueOf(150));
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setTitulo("A historia dos pets");
        livro2.setDataPublicacao(LocalDate.of(2002, 2, 25));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarAutorComLivros(){
        var autor = UUID.fromString("8f9ad19f-0a66-4b19-a901-d5c0f19bbbe5");
        var livro = repository.findById(autor).get();

        //buscar os livros do autor
        List<Livro> livroslista = livroRepository.findByAutor(livro);
        livro.setLivros(livroslista);

        livro.getLivros().forEach(System.out::println);
    }
}

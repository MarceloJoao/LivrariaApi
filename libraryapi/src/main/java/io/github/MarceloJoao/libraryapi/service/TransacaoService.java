package io.github.MarceloJoao.libraryapi.service;


import io.github.MarceloJoao.libraryapi.model.Autor;
import io.github.MarceloJoao.libraryapi.model.GeneroLivro;
import io.github.MarceloJoao.libraryapi.model.Livro;
import io.github.MarceloJoao.libraryapi.repository.AutorRepository;
import io.github.MarceloJoao.libraryapi.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("3cfc1d7f-4ca9-47ae-9cc9-646cae06715e")).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2025, 4, 20));

        livroRepository.save(livro);

    }

    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("João Souza");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2007, 1, 31));

        autorRepository.save(autor);

        //if(autor.getNome().equals("Maria Luiza")){
            //throw new RuntimeException("Roolback");
        //}

        Livro livro = new Livro();
        livro.setIsbn("9863535");
        livro.setPreco(BigDecimal.valueOf(120));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("A historia do futebol");
        livro.setDataPublicacao(LocalDate.of(1980, 2, 25));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }
}

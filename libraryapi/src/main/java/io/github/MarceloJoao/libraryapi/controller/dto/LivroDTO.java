package io.github.MarceloJoao.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.MarceloJoao.libraryapi.model.Autor;
import io.github.MarceloJoao.libraryapi.model.GeneroLivro;
import io.github.MarceloJoao.libraryapi.model.Livro;

public record LivroDTO(

        UUID id,
        String titulo,
        String isbn,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataPublicacao,

        GeneroLivro genero,
        BigDecimal preco,
        UUID autorId

) {

    public Livro mapearParaLivro(Autor autor) {
        Livro livro = new Livro();
        livro.setTitulo(this.titulo);
        livro.setIsbn(this.isbn);
        livro.setDataPublicacao(this.dataPublicacao);
        livro.setGenero(this.genero);
        livro.setPreco(this.preco);
        livro.setAutor(autor);
        return livro;
    }
}

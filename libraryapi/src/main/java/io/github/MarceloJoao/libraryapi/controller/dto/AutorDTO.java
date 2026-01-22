package io.github.MarceloJoao.libraryapi.controller.dto;

import io.github.MarceloJoao.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AutorDTO(UUID id, String nome, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dataNascimento, String nacionalidade) {

   public Autor mapearParaAutor() {
       Autor autor = new Autor();
       autor.setNome(this.nome);
       autor.setDataNascimento(this.dataNascimento);
       autor.setNacionalidade(this.nacionalidade);
       return autor;
   }
}

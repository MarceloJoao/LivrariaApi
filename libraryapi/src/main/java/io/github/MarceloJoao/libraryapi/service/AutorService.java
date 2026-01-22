package io.github.MarceloJoao.libraryapi.service;


import io.github.MarceloJoao.libraryapi.exceptions.OperacaoNaoPermitidaException;
//import io.github.MarceloJoao.libraryapi.exceptions.OperacaoNãoPermitidaException;
import io.github.MarceloJoao.libraryapi.model.Autor;
import io.github.MarceloJoao.libraryapi.repository.AutorRepository;
import io.github.MarceloJoao.libraryapi.repository.LivroRepository;
import io.github.MarceloJoao.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository repository;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterporId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuilivro(autor)){
            throw new OperacaoNaoPermitidaException("Autor possui livros cadastrados!!");
        }
        autorRepository.delete(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Autor sem id!!");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade){
       if(nome != null && nacionalidade != null){
          return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
       }

       if(nome != null){
           return autorRepository.findByNome(nome);
       }

       if(nacionalidade != null){
           return autorRepository.findByNacionalidade(nacionalidade);
       }

       return autorRepository.findAll();
    }

    public boolean possuilivro(Autor autor){
      return repository.existsByAutor(autor);
    }
}

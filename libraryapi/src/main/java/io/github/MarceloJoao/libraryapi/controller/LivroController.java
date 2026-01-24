package io.github.MarceloJoao.libraryapi.controller;

import io.github.MarceloJoao.libraryapi.controller.dto.ErroResposta;
import io.github.MarceloJoao.libraryapi.controller.dto.LivroDTO;
import io.github.MarceloJoao.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.MarceloJoao.libraryapi.model.Autor;
import io.github.MarceloJoao.libraryapi.model.Livro;
import io.github.MarceloJoao.libraryapi.repository.AutorRepository;
import io.github.MarceloJoao.libraryapi.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;
    private final AutorRepository autorRepository;

    // 🔹 CADASTRAR
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody LivroDTO dto) {
        try {
            Autor autor = autorRepository.findById(dto.autorId())
                    .orElseThrow(() -> new OperacaoNaoPermitidaException("Autor não encontrado"));

            Livro livro = dto.mapearParaLivro(autor);
            service.salvar(livro);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(livro.getId())
                    .toUri();

            return ResponseEntity.created(location).build();

        } catch (OperacaoNaoPermitidaException e) {
            var erro = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    // 🔹 LISTAR
    @GetMapping
    public ResponseEntity<List<Livro>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // 🔹 OBTER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> obterDetalhes(@PathVariable("id") String id) {
        UUID idLivro = UUID.fromString(id);
        Optional<Livro> livro = service.obterPorId(idLivro);

        return livro
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id,
            @RequestBody LivroDTO dto) {
        try {
            UUID idLivro = UUID.fromString(id);

            // Buscamos o autor para poder mapear o DTO para a entidade Livro
            Autor autor = autorRepository.findById(dto.autorId())
                    .orElseThrow(() -> new OperacaoNaoPermitidaException("Autor não encontrado"));

            Livro livroParaAtualizar = dto.mapearParaLivro(autor);

            // Chame o service passando os DOIS argumentos:
            service.atualizar(idLivro, livroParaAtualizar);

            return ResponseEntity.noContent().build();

        } catch (OperacaoNaoPermitidaException e) {
            var erro = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        } catch (IllegalArgumentException e) {
            // Caso o UUID.fromString falhe
            return ResponseEntity.badRequest().body(ErroResposta.respostaPadrao("ID inválido"));
        }
    }
    // 🔹 DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        try {
            UUID idLivro = UUID.fromString(id);
            service.deletar(idLivro);
            return ResponseEntity.noContent().build();

        } catch (OperacaoNaoPermitidaException e) {
            var erro = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }
}

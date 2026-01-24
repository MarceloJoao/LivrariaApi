package io.github.MarceloJoao.libraryapi.repository;

import io.github.MarceloJoao.libraryapi.model.Autor;
import io.github.MarceloJoao.libraryapi.model.GeneroLivro;
import io.github.MarceloJoao.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    boolean existsByAutor(Autor autor);
    boolean existsByIsbn(String isbn);
    boolean existsByIsbnAndIdNot(String isbn, UUID id);
    //Query method
    List<Livro> findByAutor(Autor autor);
    List<Livro> findByTitulo(String titulo);
    List<Livro> findByIsbn(String isbn);
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);
    List<Livro> findByDataPublicacaoBetween(LocalDate dataInicial, LocalDate dataFinal);
    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);

    //utilizando JPQL -> Referencia entidades e propriedades
    @Query("select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> ListarTodosOrdenadoPorTituloAndPreco();

    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
       select l.genero
       from Livro l
       join l.autor a
       where a.nacionalidade= 'Brasileira'
       order by l.genero
     """)
    List<String> listarGenerosAutoresBrasileiros();

    //named parameted -> parametros nomeados
    @Query("select l from Livro l where l.genero = :genero")
    List<Livro> findByGenero(@Param("genero") GeneroLivro genero);

    //positional parameted -> parametros posicionais
    @Query("select l from Livro l where l.genero = ?1")
    List<Livro> findByGeneroPosicional(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro l set l.dataPublicacao = ?1 ")
    void updateDataPublicacao(LocalDate novaData);
}
